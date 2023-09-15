/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.config.server.environment;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Scott Frederick
 */
public class HttpRequestConfigTokenProviderTests {

	private ObjectProvider<HttpServletRequest> httpRequestProvider;

	private HttpRequestConfigTokenProvider tokenProvider;

	@BeforeEach
	@SuppressWarnings("unchecked")
	public void setUp() {
		httpRequestProvider = mock(ObjectProvider.class);
		tokenProvider = new HttpRequestConfigTokenProvider(httpRequestProvider);
	}

	@Test
	public void missingHttpRequest() {
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
			when(httpRequestProvider.getIfAvailable()).thenReturn(null);
			tokenProvider.getToken();
		});
	}

	@Test
	public void missingTokenHeader() {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			when(httpRequestProvider.getIfAvailable()).thenReturn(new MockHttpServletRequest());
			tokenProvider.getToken();
		});

	}

}
