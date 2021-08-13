# Usage

Required environment variables:
- `DEVOPS_USER` username from Azure DevOps
- `DEVOPS_PAT` personal access token from Azure DevOps (permission for Build and Release)
- `GITHUB_PAT` personal access token from GitHub (used for `LiteRelease`)
- `BUILD_ID` the build ID (used for `PremiumRelease`)

## Premium release
1. Start the build and wait till Signing complete
2. Note the `buildId` and set it to `BUILD_ID`
3. Run `PremiumRelease.main()`

## Lite release
1. Edit `configure.yml` in project root (similar to `/src/main/resources/configure.yml`)
2. Run with `mvn exec:java` (equivalent to `LiteRelease.main()`)
3. Follow the prompt, choose the tag to release

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
```
