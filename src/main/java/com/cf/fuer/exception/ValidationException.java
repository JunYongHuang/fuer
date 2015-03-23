package com.cf.fuer.exception;

public class ValidationException extends Exception {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1425198337606103530L;
    
    /** The field code. */
    protected String fieldCode;
    
    /** The error code. */
    protected String errorCode;
    
    /** The args. */
    protected Object[] args;
    
    /** The default message. */
    protected String defaultMessage;

    /**
     * Instantiates a new validation error.
     * 
     * @param fieldCode the field code
     * @param errorCode the error code
     * @param args the args
     * @param defaultMessage the default message
     */
    public ValidationException(String fieldCode, String errorCode, Object[] args, String defaultMessage) {
        this.fieldCode = fieldCode;
        this.errorCode = errorCode;
        this.args = args;
        this.defaultMessage = defaultMessage;
    }
    
	/**
	 * 创建一个新的验证异常.
	 * 
	 * @param errorCode
	 *            错误代码,即定义在properties中的key
	 * @param defaultMessage
	 *            默认消息,如果找到不errorCode会显示此错误消息.
	 */
	public ValidationException(String errorCode, String defaultMessage) {
		this.errorCode = errorCode;
		this.defaultMessage = defaultMessage;
	}

    /**
     * Gets the field code.
     * 
     * @return the field code
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * Sets the field code.
     * 
     * @param fieldCode the new field code
     */
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    /**
     * Gets the error code.
     * 
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code.
     * 
     * @param errorCode the new error code
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets the args.
     * 
     * @return the args
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * Sets the args.
     * 
     * @param args the new args
     */
    public void setArgs(Object[] args) {
        this.args = args;
    }

    /**
     * Gets the default message.
     * 
     * @return the default message
     */
    public String getDefaultMessage() {
        return defaultMessage;
    }

    /**
     * Sets the default message.
     * 
     * @param defaultMessage the new default message
     */
    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
    
}
