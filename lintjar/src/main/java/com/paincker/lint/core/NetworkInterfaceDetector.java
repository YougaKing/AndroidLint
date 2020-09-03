package com.paincker.lint.core;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ClassContext;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2020/9/2 15:58
 * @description:
 */
public class NetworkInterfaceDetector extends Detector implements Detector.JavaPsiScanner, Detector.ClassScanner {

    public static final Issue ISSUE = Issue.create(
            "NetworkInterfaceUsage",
            "禁止使用 NetworkInterface.getHardwareAddress() 方法",
            "禁止使用 NetworkInterface.getHardwareAddress() 方法",
            Category.SECURITY, 5, Severity.ERROR,
            new Implementation(LogDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("getHardwareAddress");
    }

    @Override
    public List<String> getApplicableCallNames() {
        return Arrays.asList("getHardwareAddress");
    }

    @Override
    public void visitMethod(JavaContext context, JavaElementVisitor visitor, PsiMethodCallExpression call, PsiMethod method) {
        if (context.getEvaluator().isMemberInClass(method, "java.net.NetworkInterface")) {
            context.report(ISSUE, call, context.getLocation(call.getMethodExpression()), "禁止使用 NetworkInterface.getHardwareAddress() 方法");
        }
    }

    @Override
    public void checkCall(@NonNull ClassContext context, @NonNull ClassNode classNode, @NonNull MethodNode method, @NonNull MethodInsnNode call) {
        super.checkCall(context, classNode, method, call);
        String owner = call.owner;
        if (owner.startsWith("java/net/NetworkInterface")) {
            context.report(ISSUE,
                    method,
                    call,
                    context.getLocation(call),
                    "禁止使用 NetworkInterface.getHardwareAddress() 方法");
        }
    }
}