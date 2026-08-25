# CI/CD Workflows Documentation

This document explains the GitHub Actions workflows in this repository and the overall CI/CD pipeline flow for the DPS (Document Processing System) project.

## General CI/CD Pipeline Flow

The CI/CD pipeline follows a standard progression through environments:

1. **Development (Dev)**: Code is built into Docker images and deployed to Artifactory
2. **Testing (Test)**: Images are promoted from dev to test environment for QA
3. **Production (Prod)**: Images are promoted from test to prod to Artifactory

The pipeline uses:
- **GitHub Actions** for CI/CD automation
- **Artifactory** (artifacts.developer.gov.bc.ca) for Docker image storage
- **OpenShift** for container orchestration
- **GitOps** approach with a separate repository (bcgov-c/tenant-gitops-043918) for deployment manifests
- **Helm** charts for application deployment configuration

## Workflows Overview

### Core Build and Deployment Workflows

#### `main.yml`
**DEPRECATED**
**Purpose**: Build Docker images and push to OpenShift registry for development deployment
- **Trigger**: Manual dispatch with app selection and environment
- **Process**:
  - Extracts app version from Maven POM file
  - Builds Docker image using reusable workflow
  - Pushes to OpenShift external registry
  - Performs Trivy vulnerability scanning
  - Uploads scan results to GitHub Security tab

#### `dev-*.yml` (e.g., `dev-dps-payment-service.yml`)
**Purpose**: Build and deploy individual services to development environment
- **Trigger**: Manual dispatch with branch/tag selection
- **Process**:
  - Checks out specified branch/tag
  - Builds Docker image locally
  - Pushes to Artifactory with `dev` tag
  - Runs Trivy vulnerability scan
  - Updates Helm values in GitOps repository for dev environment
  - Commits changes to trigger deployment which will trigger ArgoCD

#### `test-tag-and-push.yaml`
**Purpose**: Promote images from dev to test environment
- **Trigger**: Manual dispatch with app selection
- **Process**:
  - Pulls image with `dev` tag from Artifactory
  - Re-tags as `test`
  - Pushes to Artifactory
  - Updates Helm values in GitOps repository for test environment
  - Commits changes to trigger test deployment which will trigger ArgoCD 

#### `prod-tag-and-push.yaml`
**Purpose**: Promote images from test to production environment
- **Trigger**: Manual dispatch with app selection
- **Process**:
  - Pulls image with `test` tag from Artifactory
  - Re-tags as `prod`
  - Pushes to Artifactory
  - Updates Helm values in GitOps repository for prod environment
  - Commits changes to trigger production deployment which will trigger ArgoCD

### Quality Assurance Workflows

#### `maven-test-pr.yml`
**Purpose**: Run unit tests on pull requests
- **Trigger**: Pull requests and manual dispatch
- **Process**:
  - Sets up Java 17 environment
  - Updates Git submodules
  - Runs Maven tests with all profiles

#### `maven-sonar.yml`
**Purpose**: Perform static code analysis
- **Trigger**: Push to master branch
- **Process**:
  - Sets up Java 17 environment
  - Runs Maven clean verify with SonarQube analysis
  - Sends results to configured SonarQube server

### Security and Scanning Workflows

#### `trivy-scan.yml`
**Purpose**: Standalone vulnerability scanning of container images
- **Trigger**: Manual dispatch with app selection
- **Process**:
  - Pulls latest image from OpenShift registry
  - Runs Trivy scanner for HIGH and CRITICAL vulnerabilities
  - Displays results in table format

### Infrastructure Workflows

#### `openshift-imagetagging.yml`
**Purpose**: Promote images within OpenShift ImageStreams
- **Trigger**: Manual dispatch with app, source tag, and target environment
- **Process**:
  - Uses reusable workflow to re-tag images in OpenShift
  - Promotes from dev→test or test→prod within the cluster
  **Deprecated** 

#### `dev-libs.yml`
**Purpose**: Build and deploy shared libraries
- **Trigger**: Push to main branch affecting `src/libs/**` or manual dispatch
- **Process**:
  - Uses reusable workflow to build Java libraries
  - Deploys artifacts to Nexus repository
  **Deprecated - maybe**

## Environment Details

- **Dev Environment**: Uses `develop` branch in GitOps repo
- **Test Environment**: Uses `test` branch in GitOps repo
- **Prod Environment**: Uses `main` branch in GitOps repo

## Service Abbreviations

- `dep`: dps-email-poller
- `dew`: dps-email-worker
- `dns`: dps-notification-service
- `dps`: dps-payment-service
- `dvs`: dps-validation-service
- `vnw`: vips-notification-worker

## Secrets Required

The workflows require various secrets to be configured in the repository:
- Artifactory credentials
- OpenShift cluster access
- GitOps repository SSH key

## Notes

- All deployments follow GitOps principles with automatic commits to the deployment repository
- Images are scanned for vulnerabilities at multiple stages
- The pipeline supports multiple microservices with individual deployment workflows