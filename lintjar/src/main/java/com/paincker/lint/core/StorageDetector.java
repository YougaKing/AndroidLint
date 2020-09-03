package com.paincker.lint.core;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ClassContext;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;

/**
 * @author: YougaKingWu@gmail.com
 * @created on: 2020/9/2 16:27
 * @description:
 */
public class StorageDetector extends Detector implements Detector.ClassScanner {
    public static final Issue ISSUE = Issue.create("StorageUse",
            "Don't use Environment.getExternalStorageDirectory",
            "Use Context.getFiles()",
            Category.SECURITY,
            5,
            Severity.WARNING,
            new Implementation(StorageDetector.class, Scope.CLASS_FILE_SCOPE));

    @Override
    public List<String> getApplicableCallNames() {
        return Arrays.asList("getExternalStorageDirectory", "getExternalStoragePublicDirectory");
    }

    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("getExternalStorageDirectory", "getExternalStoragePublicDirectory");
    }

    @Override
    public void checkCall(@NonNull ClassContext context, @NonNull ClassNode classNode, @NonNull MethodNode method, @NonNull MethodInsnNode call) {
        super.checkCall(context, classNode, method, call);
        String owner = call.owner;
        if (owner.startsWith("android/os/Environment")) {
            context.report(ISSUE,
                    method,
                    call,
                    context.getLocation(call),
                    "You must use `Context.getFiles()`");
        }
    }
}