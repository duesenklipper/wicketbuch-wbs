/**
 * Copyright 2009 Roland Foerther, Carl-Eric-Menzel, Olaf Siefart
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.senacor.wbs.web.project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Puzzle {
	static void test() {
		// i
		// j
		int i = 1;
		double j = 1;
		System.out.println(i <= j && j <= i && i != j);
	}

	static void test1() throws Exception {
		InputStream i = new BufferedInputStream(null);
		OutputStream o = new BufferedOutputStream(null);
		try {
			// copy ...
		} finally {
			i.close();
			o.close();
		}
	}

	static int test2() {
		try {
			System.exit(0);
			return 1;
		} catch (Exception e) {
			return 2;
		} finally {
			return 3;
		}
	}

	interface f1 {
		void f() throws IOException;
	}

	interface f2 {
		void f() throws InterruptedException;
	}

	interface f3 extends f1, f2 {
	}

	class x implements f3 {
		public void f() {
			System.out.println("test");
		}
	}

	public static void main(String[] args) {
		http: // www.google.de
		test();
	}
}
