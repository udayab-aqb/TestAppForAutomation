# Test Coverage Report System

This directory contains a comprehensive test coverage reporting system based on the **TEST_CHECKLIST.md** standards.

## 📁 Files Overview

| File | Description |
|------|-------------|
| **test-coverage-config.yml** | Configuration file with metrics, calculations, and checkpoints |
| **generate_coverage_report.py** | Python script to generate reports from the YAML config |
| **TEST_COVERAGE_REPORT.md** | Auto-generated coverage report (do not edit manually) |
| **.github/workflows/test-coverage-report.yml** | GitHub Actions workflow for automated report generation |

---

## 🚀 Quick Start

### Generate Report Manually

```bash
# Install dependencies (first time only)
pip install pyyaml

# Generate the report
python3 generate_coverage_report.py test-coverage-config.yml
```

This will create/update `TEST_COVERAGE_REPORT.md` with current metrics.

---

## ⚙️ Configuration

### Update Metrics

Edit `test-coverage-config.yml` to update your test metrics:

```yaml
metrics:
  total_test_cases: 10        # Update this
  automated_test_cases: 8     # Update this
  manual_test_cases: 2        # Update this
  
  # Test execution results (updated after test runs)
  passed_tests: 0
  failed_tests: 0
  skipped_tests: 0
```

### Update Targets

Set your coverage targets:

```yaml
metrics:
  targets:
    automation_coverage_target: 100   # Target %
    test_pass_rate_target: 95         # Target %
    code_coverage_target: 80          # Target %
```

### Update Compliance Status

Track compliance across categories:

```yaml
compliance:
  categories:
    - name: "POM Structure"
      compliance_percent: 100
      status: "compliant"  # compliant, mostly_compliant, non_compliant
      notes: "All page objects follow standards"
```

---

## 🤖 Automated Reporting (GitHub Actions)

The workflow `.github/workflows/test-coverage-report.yml` automatically:

### Triggers
- ✅ On push to `main`, `develop*`, `master` branches
- ✅ On pull requests
- ✅ Weekly on Mondays at 9 AM
- ✅ Manual trigger via GitHub Actions UI

### What It Does
1. Generates test coverage report
2. Uploads report as artifact (30-day retention)
3. Commits updated report to repository
4. Comments on PRs with coverage metrics
5. Updates metrics from TestNG test results (optional)

### View Reports
- **In GitHub Actions:** Go to Actions → Test Coverage Report → Artifacts
- **In Repository:** Check `TEST_COVERAGE_REPORT.md`
- **In PRs:** Automated comment with key metrics

---

## 📊 What's Included in Reports

### 1. Test Coverage Metrics
- Total, automated, and manual test counts
- Automation coverage percentage
- Test execution results (passed/failed/skipped)
- Pass rate calculations

### 2. Metric Calculations
All formulas with current values:
- Automation Coverage = `(automated_test_cases / total_test_cases) × 100`
- Test Pass Rate = `(passed_tests / automated_test_cases) × 100`
- Automation Gap = `total_test_cases - automated_test_cases`

### 3. Quality Checkpoints
- ✅ Framework coverage & maturity
- ✅ Test case quality (pre-automation)
- ✅ Code quality - POM structure
- ✅ Code quality - Test class standards
- ✅ Selector quality standards

### 4. Selector Strategy
- Preference hierarchy (ID → Name → CSS → XPath)
- Good vs bad XPath examples
- Best practices

### 5. Compliance Status
Tracking compliance across:
- POM Structure
- Selector Strategy
- Test Standards
- Documentation
- Code Review

### 6. Risk Assessment
- Low, medium, and high-risk items
- Mitigation strategies

### 7. Recommendations
- Short-term (next sprint)
- Medium-term (next quarter)
- Long-term (next 6 months)

---

## 🔄 Updating After Test Execution

### Manual Update

After running tests, update the YAML file with results:

```yaml
metrics:
  passed_tests: 8
  failed_tests: 0
  skipped_tests: 0
```

Then regenerate the report.

### Automatic Update (CI/CD)

The GitHub Actions workflow can automatically extract test results from TestNG:

```bash
# Triggered automatically or manually with:
# GitHub Actions → Test Coverage Report → Run workflow → Update metrics: true
```

---

## 📈 Tracking Trends

Add historical data to track coverage over time:

```yaml
historical_data:
  - date: "2025-12-11"
    total_tests: 10
    automated_tests: 8
    coverage_percent: 80
    pass_rate: 100
    
  - date: "2025-12-18"
    total_tests: 12
    automated_tests: 10
    coverage_percent: 83
    pass_rate: 95
```

---

## 🎯 Custom Report Generation

### Generate with Custom Output File

```bash
python3 generate_coverage_report.py test-coverage-config.yml custom-report.md
```

### Modify Report Sections

Edit `generate_coverage_report.py` and customize the `generate_report()` function to include/exclude sections.

---

## 🔧 Integration

### Jenkins Integration

Add to your Jenkinsfile:

```groovy
stage('Generate Coverage Report') {
    steps {
        sh 'pip install pyyaml'
        sh 'python3 generate_coverage_report.py test-coverage-config.yml'
        
        // Publish as artifact
        archiveArtifacts artifacts: 'TEST_COVERAGE_REPORT.md', fingerprint: true
        
        // Publish to workspace
        publishHTML([
            reportDir: '.',
            reportFiles: 'TEST_COVERAGE_REPORT.md',
            reportName: 'Test Coverage Report'
        ])
    }
}
```

### Slack/Email Notifications

Configure notifications in the YAML:

```yaml
integration:
  notifications:
    slack:
      enabled: true
      webhook_url: "your-webhook-url"
      channel: "#qa-reports"
```

---

## 📋 Best Practices

1. **Update Regularly**: Update metrics after each sprint/release
2. **Review Reports**: Review coverage reports in team meetings
3. **Track Trends**: Maintain historical data to see progress
4. **Set Targets**: Define realistic coverage targets
5. **Automate**: Use GitHub Actions for consistent reporting
6. **Version Control**: Commit both config and generated reports

---

## 🆘 Troubleshooting

### Report Generation Fails

```bash
# Check if YAML is valid
python3 -c "import yaml; yaml.safe_load(open('test-coverage-config.yml'))"

# Check Python dependencies
pip install pyyaml
```

### GitHub Actions Not Running

- Check workflow file syntax
- Ensure workflow has permissions to commit
- Check branch protection rules

### Metrics Not Updating

- Verify TestNG results are in `target/surefire-reports/testng-results.xml`
- Check Maven test execution
- Review GitHub Actions logs

---

## 📚 References

- [TEST_CHECKLIST.md](TEST_CHECKLIST.md) - QA Standards & Guidelines
- [GIT_REPORT.md](GIT_REPORT.md) - Git-based project report
- [pom.xml](pom.xml) - Maven configuration
- [Jenkinsfile](Jenkinsfile) - CI/CD configuration

---

## 📝 License

This reporting system is part of the TestAppForAutomation project.

---

**Last Updated:** 11/12/2025  
**Maintained By:** QA Team
