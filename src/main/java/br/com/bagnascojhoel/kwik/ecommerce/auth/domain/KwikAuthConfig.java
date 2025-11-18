package br.com.bagnascojhoel.kwik.ecommerce.auth.domain;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.RawSecret;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithConverter;
import java.time.Duration;
import org.eclipse.microprofile.config.spi.Converter;

@ConfigMapping(prefix = "kwik.auth")
public interface KwikAuthConfig {

  @WithConverter(RawPasswordConverter.class)
  RawSecret adminPassword();

  @WithConverter(RawPasswordConverter.class)
  RawSecret jwtSecret();

  String jwtIssuer();

  String jwtAudience();

  @WithConverter(MinutesDurationConverter.class)
  Duration jwtDurationMinutes();

  class RawPasswordConverter implements Converter<RawSecret> {

    @Override
    public RawSecret convert(final String aString)
        throws IllegalArgumentException, NullPointerException {
      if (aString == null) {
        throw new NullPointerException("Password cannot be null");
      }
      return RawSecret.of(aString.trim());
    }
  }

  class MinutesDurationConverter implements Converter<Duration> {

    @Override
    public Duration convert(final String aString)
        throws IllegalArgumentException, NullPointerException {
      if (aString == null) {
        throw new NullPointerException("Duration cannot be null");
      }
      return Duration.ofMinutes(Long.parseLong(aString));
    }
  }
}
