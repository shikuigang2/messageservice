package com.ztth.core.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseModel implements Serializable {


}
