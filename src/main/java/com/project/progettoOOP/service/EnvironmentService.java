package com.project.progettoOOP.service;

import com.project.progettoOOP.model.Environment;
import com.project.progettoOOP.model.EnvironmentCollection;

import java.util.Collection;

public interface EnvironmentService {
    public abstract void createEnvironment(Environment environment);
    public abstract void updateEnvironment(Integer id, Environment environment);
    public abstract void deleteEnvironment(Integer id);
    public abstract EnvironmentCollection getEnvironment();
}
