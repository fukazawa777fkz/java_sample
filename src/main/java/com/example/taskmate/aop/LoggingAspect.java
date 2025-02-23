package com.example.taskmate.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    //    ■アノテーション（処理開始に呼ぶ）
    //    @Before                                                     ：処理開始時に呼ぶアノテーション（Before Advice）
    //    ■以降オプション
    //    @Before("execution                                          ：対象を指定して実行するというポイントカット式（オプション）
    //    @Before("execution(*                                        ：全ての戻り値
    //    @Before("execution(* com.example.taskmate.service           ：サービスが対象
    //    @Before("execution(* com.example.taskmate.service.*.*       ：全てのクラスとメソッド
    //    @Before("execution(* com.example.taskmate.service.*.*(..)   ：引数は0～何個でも
    @Before("execution(* com.example.taskmate.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        this.outputLog("処理開始", joinPoint);
    }
    @AfterReturning("execution(* com.example.taskmate.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        this.outputLog("処理終了", joinPoint);
    }

    // 共通ログ出力メソッド
    private void outputLog(String str, JoinPoint joinPoint) {
        // 現在時刻文字列取得
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strNow = LocalDateTime.now().format(formatter);
        // クラス名・メソッド名取得
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        // メソッド引数取得
        Object[] args = joinPoint.getArgs();
        String argsString = args.length > 0 ? " 引数: " + getArgsWithNames(joinPoint) : "";
        // ログ表示
        System.out.println(
                strNow + ":" + str + " : " +
                        className + "." + methodName + "()" + argsString);
    }

    // 引数の名前と値を取得するメソッド
    private String getArgsWithNames(JoinPoint joinPoint) {
        StringBuilder result = new StringBuilder();
        try {
            Method method = Arrays.stream(joinPoint.getTarget().getClass().getMethods())
                    .filter(m -> m.getName().equals(joinPoint.getSignature().getName()))
                    .findFirst()
                    .orElse(null);
            if (method != null) {
                Parameter[] parameters = method.getParameters();
                Object[] args = joinPoint.getArgs();
                for (int i = 0; i < parameters.length; i++) {
                    result.append(parameters[i].getName()).append("=").append(args[i]);
                    if (i < parameters.length - 1) {
                        result.append(", ");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
