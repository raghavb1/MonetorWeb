package com.champ.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.champ.base.request.BaseRequest;
import com.champ.base.response.BaseResponse;
import com.champ.core.enums.ApiResponseCodes;
import com.champ.core.exception.MonetorServiceException;

@Aspect
@Component("monetorServiceAspect")
public class MonetorServiceAspect {

	private static final Logger LOG = LoggerFactory.getLogger(MonetorServiceAspect.class);

	@Pointcut("execution(* com.champ.web.external.controller.MonetorWebServiceController.*(..))")
	public void methodPointcut() {
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void isRequestMapping() {
	}

	@SuppressWarnings("rawtypes")
	@Around("methodPointcut() && isRequestMapping()")
	public Object forwardRequestContext(ProceedingJoinPoint pjp) throws Throwable {
		BaseRequest request = null;
		Object[] arguments = pjp.getArgs();
		for (int i = 0; i < arguments.length; ++i) {
			Object arg = arguments[i];
			if (arg instanceof BaseRequest) {
				request = (BaseRequest) arg;
				break;
			}
		}
		BaseResponse response = null;
		try {
			response = (BaseResponse) pjp.proceed();
			response.setCode(ApiResponseCodes.SUCCESS.getCode());
			response.setSuccess(true);
			response.setMessage(ApiResponseCodes.SUCCESS.getMessage());
		} catch (MonetorServiceException mse) {
			Class target = ((MethodSignature) pjp.getSignature()).getReturnType();
			response = (BaseResponse) target.newInstance();
			response.setSuccess(false);
			response.setCode(mse.getCode());
			response.setMessage(mse.getMessage());
			LOG.error("Error processing request from email {}", request.getMobile(), mse);
		} catch (Exception ex) {
			Class target = ((MethodSignature) pjp.getSignature()).getReturnType();
			response = (BaseResponse) target.newInstance();
			response.setSuccess(false);
			response.setCode(ApiResponseCodes.INTERNAL_SERVER_ERROR.getCode());
			response.setMessage(ApiResponseCodes.INTERNAL_SERVER_ERROR.getMessage());
			LOG.error("Error processing request from email {}", request.getMobile(), ex);
		}
		return response;
	}
}
