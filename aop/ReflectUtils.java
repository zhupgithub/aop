zhupeng com.zhupeng.destiny.utils;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;

public class ReflectUtils {
	
	/**
	 * 
	 * @Description: 获得相应方法的参数名和参数值
	 * @param @param cls
	 * @param @param clazzName
	 * @param @param methodName
	 * @param @param args
	 * @param @return
	 * @param @throws NotFoundException   
	 * @return Map<String,Object>  
	 * @throws
	 * @author zhupeng
	 * @date 2019年1月15日
	 */
	public static Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException { 
		Map<String,Object > map=new HashMap<String,Object>();
		
        ClassPool pool = ClassPool.getDefault();  
        //ClassClassPath classPath = new ClassClassPath(this.getClass());  
        ClassClassPath classPath = new ClassClassPath(cls);  
        pool.insertClassPath(classPath);  
          
        CtClass cc = pool.get(clazzName);  
        CtMethod cm = cc.getDeclaredMethod(methodName);  
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();  
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);  
        if (attr == null) {  
            // exception  
        }  
       // String[] paramNames = new String[cm.getParameterTypes().length];  
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;  
        for (int i = 0; i < cm.getParameterTypes().length; i++){  
            map.put( attr.variableName(i + pos),args[i]);//paramNames即参数名  
        }  
        
        //Map<>
        return map;  
    }  

}
