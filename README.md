# Usage

## Prerequisites
Setup Azure DevOps CLI. Full doc: https://learn.microsoft.com/en-us/azure/devops/cli/?view=azure-devops
1. az extension add --name azure-devops
2. az login

Required environment variables:
- `GITHUB_PAT` personal access token from GitHub (used for `LiteRelease`)
- `BUILD_ID` the build ID (used for `PremiumRelease`)
- `DEVOPS_USER` username from Azure DevOps (deprecated, if using Azure DevOps CLI)
- `DEVOPS_PAT` personal access token from Azure DevOps (deprecated, if using Azure DevOps CLI, permission for Build and Release)

## Lite release
1. Edit `configure.yml` in project root (similar to `/src/main/resources/configure.yml`)
2. Run with `mvn exec:java` (equivalent to `LiteRelease.main()`)
3. Follow the prompt, choose the tag to release

## Premium release
1. Start the build and wait till Signing complete
2. Note the `buildId` and set it to `BUILD_ID`
3. Run `PremiumRelease.main()`

## Swagger specs

https://github.com/MicrosoftDocs/vsts-rest-api-specs

## Configuration for autorest

> see https://aka.ms/autorest

This is the AutoRest configuration file for Dev.

```yaml
title: DevClient
namespace: com.azure.dev
license-header: MICROSOFT_MIT_SMALL

input-file:
- build.json
- pipelines.json

output-folder: .

regenerate-pom: false

java: true
fluent: LITE
generate-samples: false

pipeline:
  modelerfour:
    additional-checks: true
```
