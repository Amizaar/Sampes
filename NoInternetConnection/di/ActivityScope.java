package #.di;

/**
 * Created by Roman Silka on 11/24/16.
 */

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {}
