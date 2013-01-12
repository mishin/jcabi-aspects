/**
 * Copyright (c) 2012-2013, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.aspects.aj;

import com.jcabi.aspects.Cacheable;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test case for {@link MethodCacher}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class MethodCacherTest {

    /**
     * MethodCacher can cache calls.
     * @throws Throwable If something goes wrong
     * @checkstyle IllegalThrows (5 lines)
     */
    @Test
    public void cachesSimpleCall() throws Throwable {
        this.call(new Object[] {null});
    }

    /**
     * Call it with the provided params.
     * @param args The args
     * @throws Throwable If something goes wrong
     * @checkstyle IllegalThrows (5 lines)
     */
    private void call(final Object[] args) throws Throwable {
        final MethodCacher cacher = new MethodCacher();
        final Method method = MethodCacherTest.Foo.class
            .getMethod("download", String.class);
        final MethodSignature sig = Mockito.mock(MethodSignature.class);
        Mockito.doReturn(method).when(sig).getMethod();
        final ProceedingJoinPoint point =
            Mockito.mock(ProceedingJoinPoint.class);
        Mockito.doReturn(sig).when(point).getSignature();
        Mockito.doReturn(args).when(point).getArgs();
        cacher.wrap(point);
    }

    /**
     * Dummy class, for tests above.
     */
    private static final class Foo {
        /**
         * Download some text.
         * @param text Some text
         * @return Downloaded text
         */
        @Cacheable
        public String download(final String text) {
            return "done";
        }
    }

}
