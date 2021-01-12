package br.edu.ifpb.gestao.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import br.edu.ifpb.gestao.core.validation.ValidacaoException;
import br.edu.ifpb.gestao.domain.exception.EntidadeEmUsoException;
import br.edu.ifpb.gestao.domain.exception.EntidadeNaoEncontradaException;
import br.edu.ifpb.gestao.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request) {
		return handleValidationInternal(ex, ex.getBindingResult(), HttpStatus.BAD_REQUEST, new HttpHeaders(), request);
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		Problem problem = createProblemBuilder(status, problemType, "O tipo do arquivo deve ser PDF").build();
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return handleValidationInternal(ex, ex.getBindingResult(), status, headers, request);
	}
	
	//Exceção lançada quando falta algum campo obrigatorio no corpo da requisição
		private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
				HttpStatus status, HttpHeaders headers, WebRequest request) {

			ProblemType problemType = ProblemType.DADOS_INVALIDOS;
			String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

			List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
				String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

				String name = objectError.getObjectName();

				if (objectError instanceof FieldError) {
					name = ((FieldError) objectError).getField();
				}

				return Problem.Object.builder().name(name).userMessage(message).build();
			}).collect(Collectors.toList());

			Problem problem = createProblemBuilder(status, problemType, detail).objects(problemObjects)
					.build();

			return handleExceptionInternal(ex, problem, headers, status, request);
		}

	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		}else if(body instanceof String) {
			body = Problem.builder()
					.title((String)body)
					.status(status.value())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	//Exceção lançada quando o usuario da API tenta acessar um recurso que não existe
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
	            ex.getRequestURL());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {
	    
	    if (ex instanceof MethodArgumentTypeMismatchException) {
	        return handleMethodArgumentTypeMismatch(
	                (MethodArgumentTypeMismatchException) ex, headers, status, request);
	    }

	    return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	
	//Exceção lançada quando o tipo do parametro da url está errado
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

	    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.build();

	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	//Exceção lançada quando existe algum erro de sintaxe no corpo da requisição
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBidingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido, verifique a sintaxe";
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	//Exceção lançada quando no corpo da requisição exista alguma propriedade que não existe no nosso modelo de dominio
	private ResponseEntity<Object> handlePropertyBidingException(PropertyBindingException ex, HttpHeaders headers, 
			HttpStatus status, WebRequest request) {
		
		//Pega o nome da propriedade que não existe
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' não existe. Corrija ou remova a propriedade.", path);
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	
	//Exceção lançada quando uma propriedade do corpo da requisição recebe um valor inválido
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		//Pega o nome da propriedade que recebeu o valor inválido
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é um tipo inválido. "
				+ "Corrija e informe um valor compatível com o tipo %s.", path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).headers(headers).build();
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType,
			String detail) {
		return Problem.builder()
				.timestamp(OffsetDateTime.now())
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
	
}