package org.foobar;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.Priorities;
import edu.umd.cs.findbugs.SourceLineAnnotation;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.classfile.DescriptorFactory;


public class FooBarDetector implements Detector {

	private final BugReporter bugReporter;
	
	public FooBarDetector(BugReporter bugReporter) {
		this.bugReporter = bugReporter;
	}
	@Override
	public void report() { }

	@Override
	public void visitClassContext(ClassContext classContext) {
		JavaClass javaClass = classContext.getJavaClass();
		Method[] methods = javaClass.getMethods();
		
		for(Method method : methods){
			String methodName = method.getName();
			if("foo".equalsIgnoreCase(methodName) || "bar".equalsIgnoreCase(methodName)){
				BugInstance bug = new BugInstance("FOO_BAR_METHOD_NAME", Priorities.HIGH_PRIORITY)
					.addClass(classContext.getClassDescriptor())
					.addMethod(javaClass, method)
					.addSourceLine(
							SourceLineAnnotation.forFirstLineOfMethod(
									DescriptorFactory.instance()
										.getMethodDescriptor(javaClass, method)
							));
				
				bugReporter.reportBug(bug);
			}
		}

	}

}
