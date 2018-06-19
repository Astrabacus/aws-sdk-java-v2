/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.core.client.builder;

import java.util.function.Consumer;
import software.amazon.awssdk.annotations.SdkPublicApi;
import software.amazon.awssdk.core.client.config.ClientAsyncConfiguration;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;

/**
 * This includes required and optional override configuration required by every async client builder. An instance can be acquired
 * by calling the static "builder" method on the type of async client you wish to create.
 *
 * <p>Implementations of this interface are mutable and not thread-safe.</p>
 *
 * @param <B> The type of builder that should be returned by the fluent builder methods in this interface.
 * @param <C> The type of client generated by this builder.
 */
@SdkPublicApi
public interface SdkAsyncClientBuilder<B extends SdkAsyncClientBuilder<B, C>, C> {
    /**
     * Specify overrides to the default SDK async configuration that should be used for clients created by this builder.
     */
    B asyncConfiguration(ClientAsyncConfiguration clientAsyncConfiguration);

    /**
     * Similar to {@link #asyncConfiguration(ClientAsyncConfiguration)}, but takes a lambda to configure a new
     * {@link ClientAsyncConfiguration.Builder}. This removes the need to called {@link ClientAsyncConfiguration#builder()}
     * and {@link ClientAsyncConfiguration.Builder#build()}.
     */
    default B asyncConfiguration(Consumer<ClientAsyncConfiguration.Builder> clientAsyncConfiguration) {
        return asyncConfiguration(ClientAsyncConfiguration.builder().apply(clientAsyncConfiguration).build());
    }

    /**
     * Sets the {@link SdkAsyncHttpClient} that the SDK service client will use to make HTTP calls. This HTTP client may be
     * shared between multiple SDK service clients to share a common connection pool. To create a client you must use an
     * implementation specific builder. Note that this method is only recommended when you wish to share an HTTP client across
     * multiple SDK service clients. If you do not wish to share HTTP clients, it is recommended to use
     * {@link #httpClientBuilder(SdkAsyncHttpClient.Builder)} so that service specific default configuration may be applied.
     *
     * <p>
     * <b>This client must be closed by the caller when it is ready to be disposed. The SDK will not close the HTTP client
     * when the service client is closed.</b>
     * </p>
     *
     * @return This builder for method chaining.
     */
    B httpClient(SdkAsyncHttpClient httpClient);

    /**
     * Sets a custom HTTP client builder that will be used to obtain a configured instance of {@link SdkAsyncHttpClient}. Any
     * service specific HTTP configuration will be merged with the builder's configuration prior to creating the client. When
     * there is no desire to share HTTP clients across multiple service clients, the client builder is the preferred way to
     * customize the HTTP client as it benefits from service specific defaults.
     *
     * <p>
     * <b>Clients created by the builder are managed by the SDK and will be closed when the service client is closed.</b>
     * </p>
     *
     * @return This builder for method chaining.
     */
    B httpClientBuilder(SdkAsyncHttpClient.Builder httpClientBuilder);
}
