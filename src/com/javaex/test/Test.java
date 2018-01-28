package com.javaex.test;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String a = "김수한무 거북이와두루미 삼천갑자 동방삭";
		System.out.println(a.replace(" ", "%"));
		String b = "김수한무 거북이와두루미 삼천갑자 동방삭";
		System.out.println(b.replaceAll(" ", "%"));
		String c = "김수한무 거북이와두루미 삼천갑자 동방삭";
		System.out.println(" ".concat(c));
		System.out.println(c.concat("ff"));
		
		
		
		
		
	}

}
