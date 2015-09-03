package test.com.edifixio.amine.applicatif;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;


public class TestAOP {
	public class Foo
	{
		private String bar;
	
	    public void setBar(String bar)
	    {	System.out.println("i m in the setter");
	        //throw new UnsupportedOperationException("should not go here");
	    	this.bar=bar;
	    }
	    
	    public String getBar()
	    {	System.out.println("i m in the getter");
	        //throw new UnsupportedOperationException("should not go here");
	    	return bar;
	    }

	    public void redirected()
	    {
	        System.out.println("Yiha");
	    }
	}
public static void main(String[] args){
	Foo foo = new TestAOP(). new Foo();
	ProxyFactory pf = new ProxyFactory(foo);

	pf.addAdvice(new MethodInterceptor()
	{
	    public Object invoke(MethodInvocation mi) throws Throwable
	    {
	        if (mi.getMethod().getName().startsWith("get"))
	        {
	            Method redirect = mi.getThis().getClass().getMethod("setBar",String.class);
	            redirect.invoke(mi.getThis(),"btte");
	        }
	        return mi.getMethod().invoke(mi.getThis());
	    }
	});

	Foo proxy = (Foo) pf.getProxy();
	System.out.println(proxy.getBar());
}
}
