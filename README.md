# Demo - Java Security        
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fabien-martin-sonarsource_demo-java-security&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fabien-martin-sonarsource_demo-java-security)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fabien-martin-sonarsource_demo-java-security&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=fabien-martin-sonarsource_demo-java-security)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=fabien-martin-sonarsource_demo-java-security&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=fabien-martin-sonarsource_demo-java-security)

## Use case
This example demonstrates:
- Vulnerabilities
- Security Hotspots

It also demonstrates the possibility to define your own custom sources, sanitizers and sinks to detect more injection cases
(or avoid false positives)

## Usage

Run `./run.sh`

This will:
- Delete the project key **training:java-security** if it exists in SonarQube (to start from a scratch)
- Run `mvn clean verify sonar:sonar` to re-create the project

Project consists of a single class (`Insecure.java`) with a number of Vulnerabilities and Security Hotspots.

## Custom security configuration 
At the bottom of the class you see a bunch of methods that demonstrate custom injections.
- The method without sanitization (`doSomething()`) has an injection vulnerability
- The method with custom sanitization (`doSomethingSanitized()`) has no vulnerability

The custom security configuration file is in the root directory [here](s3649JavaSqlInjectionConfig.json)

## Secrets

```sh
Settings → Editor → Live Template
1. Sélectionne le groupe Java (ou crée un groupe "demo" avec + → Template Group)                                                                                                                                                                                                                 
  2. Clique + → Live Template                                                                                                                                                                                                                                                                      
  3. Remplis :                                                                                                                                                                                                                                                                                     
    - Abbreviation : secret (ou ce que tu veux)                                                                                                                                                                                                                                                    
    - Description : Demo hardcoded secret                                                                                                                                                                                                                                                          
    - Template text :                                                                                                                                                                                                                                                                              
  private static final String ADMIN_PASSWORD = "ku71CpsLfn8NYhhforGzCRL0";                                                                                                                                                                                                                         
  4. En bas du panneau : Define → coche Java: declaration (pour que le template marche au niveau classe)                                                                                                                                                                                           
  5. Apply / OK
  
→ Dans la classe, tape secret puis Tab
```

 ## Agents

```sh

# CLI
sonar analyze secrets *

# AC/DC
claude --resume "secret-detection-hook"

# MCP
claude --resume security-vulnerabilities-review
```