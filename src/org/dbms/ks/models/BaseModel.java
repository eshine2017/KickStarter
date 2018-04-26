package org.dbms.ks.models;

import static org.dbms.ks.models.ColumnConstants.EMPTY_VALUE;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;

public abstract class BaseModel {
	private static ThreadLocal<Boolean> isAutoJoinEnabled = new ThreadLocal<>();
	
	JSONObject baseObject;
	
	protected BaseModel(JSONObject json) throws ValidationException {
		baseObject = json;
	//	validateNonNull();
		validate();
		if(isAutoJoinEnabled()) {
			autoJoin();
		}
	}
	
	@Override
	public String toString() {
		return _getRaw().toString();
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T get(String key, T defaultValue) {
		return (T) coerce((baseObject.has(key) ? baseObject.get(key) : defaultValue), defaultValue);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T get(String key) {
		return (T) get(key, EMPTY_VALUE);
	}
	
	public JSONObject _getRaw() {
		return baseObject;
	}
	
	public <T extends BaseModel>T cast(Class<T> schema ) {
		try {			
			Constructor<T> constructor = schema.getDeclaredConstructor(JSONObject.class);
			constructor.setAccessible(true);
			return (T)constructor.newInstance(baseObject);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BaseModel>T join(Class<? extends BaseModel> schema ) {
		BaseModel newRow = null;
		try {			
			Method joinMethod = findMethod(getClass(), schema);
			newRow = (BaseModel)joinMethod.invoke(this);
			_getRaw().put("j_" + schema.getSimpleName().toLowerCase(), newRow._getRaw());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return (T) newRow;
	}

	// TODO we dont have an internal representation of the schema
	// so cant compute join dag automatically :(
	
	@SafeVarargs
	@SuppressWarnings("unchecked")
	public final <T extends BaseModel> T joinMultiple(Class<? extends BaseModel> ...schemas) {
		for(Class<? extends BaseModel> joinSchema : schemas) {
			join(joinSchema);
		}
		return (T) this;
	}
	
	@SuppressWarnings("serial")
	public class ValidationException extends Exception{
		public ValidationException(String message) {
			super(message);
		}
	}
	
	public static boolean isAutoJoinEnabled() {
		return isAutoJoinEnabled.get()!=null && isAutoJoinEnabled.get();
	}
	
	public static void setJoinFlag(boolean status) {
		isAutoJoinEnabled.set(status);
	}
	
	protected abstract void validate() throws ValidationException;
	
	protected abstract void autoJoin();

	@SuppressWarnings("unused")
	private void validateNonNull() throws ValidationException {
		for(Field f : this.getClass().getDeclaredFields()) {
			if(f.getName().equals("VALIDATION_LIST")) {
				try {
					f.setAccessible(true);
					@SuppressWarnings("unchecked")
					List<String> nonNullList = (List<String>)f.get(null);
					for(String field : nonNullList) {
						if(! baseObject.has(field)) {
							throw new ValidationException("{" + field + "} is not present in model {" + getClass().getName() +"}");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	// Coerces model attributes to enable runtime type determination
	private static Object coerce(Object input, Object defaultValue) {
		Object coercedResult = input;
		
		if(input.getClass() == defaultValue.getClass()) {
			return input;
		} else if(input.getClass().equals(String.class)) {
			if(defaultValue.getClass().equals(Integer.class)) {
				coercedResult =  Integer.valueOf((String)input);
			} else if(defaultValue.getClass().equals(Float.class)) {
				coercedResult =  Float.valueOf((String) input);
			} else if(defaultValue.getClass().equals(Long.class)) {
				coercedResult = Long.valueOf((String) input);
			}
		} else if(input.getClass().equals(BigDecimal.class)) {
			if(defaultValue.getClass().equals(Integer.class)) {
				coercedResult = ((BigDecimal)input).intValue();
			} else if(defaultValue.getClass().equals(Float.class)) {
				coercedResult = ((BigDecimal)input).floatValue();
			} else if(defaultValue.getClass().equals(Long.class)) {
				coercedResult = ((BigDecimal)input).longValue();
			}
		}
		return coercedResult;
	}
	
	private static Method findMethod(Class<? extends BaseModel> baseSchema, Class<? extends BaseModel> joinSchema) throws Exception {
		Method joinMethod = null;
		for(Method m : baseSchema.getDeclaredMethods()) {
			if(m.getReturnType() == joinSchema) {
				m.setAccessible(true);
				joinMethod = m;
				break;
			}
		}
		return joinMethod;
	}
}
