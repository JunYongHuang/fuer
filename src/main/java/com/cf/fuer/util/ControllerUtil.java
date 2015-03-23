package com.cf.fuer.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.properties.SortOrderEnum;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;

import com.cf.fuer.exception.ObjectNotFoundException;
import com.cf.fuer.exception.ValidationException;
import com.cf.fuer.webapp.bean.SearchBean;

public final class ControllerUtil {

	/** The log. */
	static Log log = LogFactory.getLog(ControllerUtil.class);

	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 */
	private ControllerUtil() {

	}

	/**
	 * Reject errors.
	 * 
	 * @param validationException
	 *            the validation exception
	 * @param error
	 *            the error
	 */
	public static void rejectErrors(ValidationException validationException, BindException error) {
		String fieldCode = validationException.getFieldCode();
		if (fieldCode != null) {
			error.rejectValue(fieldCode, validationException.getErrorCode(), validationException.getArgs(), validationException.getDefaultMessage());
		} else {
			error.reject(validationException.getErrorCode(), validationException.getArgs(), validationException.getDefaultMessage());
		}
	}

	/**
	 * Reject errors.
	 * 
	 * @param validationException
	 *            re validation exception
	 * @param result
	 *            the binding result
	 */
	public static void rejectErrors(ValidationException validationException, BindingResult result) {
		String fieldCode = validationException.getFieldCode();
		if (fieldCode != null) {
			result.rejectValue(fieldCode, validationException.getErrorCode(), validationException.getArgs(), validationException.getDefaultMessage());
		} else {
			result.reject(validationException.getErrorCode(), validationException.getArgs(), validationException.getDefaultMessage());
		}
	}

	/**
	 * Sets a success message to be shown in the UI.
	 * <p>
	 * Message is put in the <code>HttpSession</code> and when the message is
	 * shown in the UI, the message is removed from the session.
	 * </p>
	 * 
	 * 
	 * @param request
	 *            containing a session where the message is stored.
	 * @param message
	 *            a success message.
	 */
	public static void setSuccessMessage(HttpServletRequest request, String message) {
		HttpSession session = request.getSession(false);
		session.setAttribute("successMessage", message);

	}

	/**
	 * sets a error message to shown in the UI.
	 * 
	 */
	public static void setErrorMessage(HttpServletRequest request, String message) {
		HttpSession session = request.getSession(false);
		session.setAttribute("errorMessage", message);
	}

	/**
	 * 用于search controller，设置分页，排序和页码参数
	 */
	public static void setParameter(HttpServletRequest request, SearchBean searchBean) {
		if ("asc".equals(request.getParameter("dir"))) {
			searchBean.setSortDirection(SortOrderEnum.ASCENDING);
		} else if ("desc".equals(request.getParameter("dir"))) {
			searchBean.setSortDirection(SortOrderEnum.DESCENDING);
		}
		String sortCriterion = ServletRequestUtils.getStringParameter(request, "sort", null);
		if (sortCriterion != null) {
			searchBean.setSortCriterion(sortCriterion);
		}
		int pageNumber = ServletRequestUtils.getIntParameter(request, "page", -1);
		if (pageNumber != -1) {
			searchBean.setPageNumber(pageNumber);
		}
	}
	
	/**
	 * 可在任意位置抛出,系统将转到错误页面。
	 */
	public static void throwExceptionIfNull(Object o, String message) {
		if (o == null) {
			throw new ObjectNotFoundException(message);
		}
	}

}
