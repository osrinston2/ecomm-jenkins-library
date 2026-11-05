# E-commerce Jenkins Shared Library

Reusable Jenkins pipeline functions for microservices.

## Available functions

### prepareImageMetadata()

Automatically determines:

- Git repository name
- Current Git branch
- Container image name
- Container image tag

Exports the following Jenkins environment variables:

- REPO_NAME
- IMAGE_NAME
- IMAGE_TAG