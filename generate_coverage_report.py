#!/usr/bin/env python3
"""
Enhanced Test Coverage Report Generator
Integrates test file analysis with YAML configuration
"""

import yaml
from datetime import datetime
from pathlib import Path
import sys
import os

# Import test analyzer
from analyze_tests import TestAnalyzer


def load_config(config_file='test-coverage-config.yml'):
    """Load the YAML configuration file"""
    with open(config_file, 'r') as f:
        return yaml.safe_load(f)


def calculate_metrics(config, test_summary=None):
    """Calculate all metrics based on formulas"""
    metrics = config['metrics']
    
    # Use actual test count if available from analyzer
    if test_summary:
        actual_test_count = test_summary['total_test_methods']
        # Update metrics with actual count
        metrics['automated_test_cases'] = actual_test_count
        metrics['total_test_cases'] = actual_test_count + metrics.get('manual_test_cases', 0)
    
    # Basic calculations
    total = metrics['total_test_cases']
    automated = metrics['automated_test_cases']
    manual = metrics['manual_test_cases']
    
    automation_coverage = (automated / total * 100) if total > 0 else 0
    manual_coverage = (manual / total * 100) if total > 0 else 0
    automation_gap = total - automated
    
    # Test execution metrics
    passed = metrics.get('passed_tests', 0)
    failed = metrics.get('failed_tests', 0)
    
    test_pass_rate = (passed / automated * 100) if automated > 0 else 0
    test_failure_rate = (failed / automated * 100) if automated > 0 else 0
    
    return {
        'automation_coverage': round(automation_coverage, 2),
        'manual_coverage': round(manual_coverage, 2),
        'automation_gap': automation_gap,
        'test_pass_rate': round(test_pass_rate, 2),
        'test_failure_rate': round(test_failure_rate, 2)
    }


def generate_executive_summary(config, calculated_metrics, test_summary=None):
    """Generate executive summary section"""
    report = config['report']
    metrics = config['metrics']
    
    lines = [
        f"# {report['name']}",
        "",
        f"**Generated:** {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}  ",
        f"**Project:** {report['project']}  ",
        f"**Framework:** {report['framework']}  ",
        "",
        "---",
        "",
        "## Executive Summary",
        "",
        "This automated report provides comprehensive test coverage metrics, quality checkpoints, "
        "and compliance status for the test automation framework.",
        "",
    ]
    
    if test_summary:
        lines.append(f"**Test Files Analyzed:** {test_summary['total_test_files']}  ")
        lines.append(f"**Automated Test Methods:** {test_summary['total_test_methods']}  ")
        lines.append(f"**Average Code Compliance:** {test_summary['average_compliance_score']}%  ")
        lines.append("")
    
    return "\n".join(lines)


def generate_test_analysis_section(test_summary, analyzer):
    """Generate test analysis section with summary table"""
    if not test_summary:
        return ""
    
    lines = [
        "## 📋 Test File Analysis",
        "",
        "### Summary Table",
        "",
        analyzer.generate_summary_table(),
        "",
        "### Test Method Breakdown",
        "",
    ]
    
    for test_detail in test_summary['test_details']:
        lines.append(f"#### {test_detail['file_name']}")
        lines.append("")
        lines.append(f"**Compliance Score:** {test_detail['compliance_score']}%  ")
        lines.append(f"**Total Test Methods:** {test_detail['test_count']}  ")
        lines.append("")
        
        lines.append("**Test Methods:**")
        for i, method in enumerate(test_detail['test_methods'], 1):
            lines.append(f"{i}. `{method}()`")
        lines.append("")
        
        lines.append("**Checklist Compliance:**")
        for check, passed in test_detail['checklist_compliance'].items():
            icon = "✅" if passed else "❌"
            check_name = check.replace('_', ' ').title()
            lines.append(f"- {icon} {check_name}")
        lines.append("")
        lines.append("---")
        lines.append("")
    
    # Add detailed checklist report
    lines.append(analyzer.generate_detailed_checklist_report())
    
    return "\n".join(lines)


def generate_metrics_overview(config, calculated_metrics):
    """Generate metrics overview section"""
    metrics = config['metrics']
    targets = metrics['targets']
    
    # Status indicators
    def get_status_icon(current, target):
        if current >= target:
            return "🟢"
        elif current >= target * 0.8:
            return "🟡"
        else:
            return "🔴"
    
    coverage_status = get_status_icon(
        calculated_metrics['automation_coverage'],
        targets['automation_coverage_target']
    )
    
    lines = [
        "## 1. Test Coverage Metrics",
        "",
        "### Overview",
        "| Metric | Value | Target | Status |",
        "|--------|-------|--------|--------|",
        f"| **Total Test Cases** | {metrics['total_test_cases']} | - | ✓ |",
        f"| **Automated Test Cases** | {metrics['automated_test_cases']} | - | ✓ |",
        f"| **Manual Test Cases** | {metrics['manual_test_cases']} | - | - |",
        f"| **Automation Coverage** | {calculated_metrics['automation_coverage']}% | "
        f"{targets['automation_coverage_target']}% | {coverage_status} |",
        f"| **Automation Gap** | {calculated_metrics['automation_gap']} tests | 0 | - |",
        "",
        "### Test Execution Results",
        "| Metric | Value |",
        "|--------|-------|",
        f"| Passed Tests | {metrics.get('passed_tests', 0)} |",
        f"| Failed Tests | {metrics.get('failed_tests', 0)} |",
        f"| Skipped Tests | {metrics.get('skipped_tests', 0)} |",
        f"| Pass Rate | {calculated_metrics['test_pass_rate']}% |",
        "",
    ]
    
    return "\n".join(lines)


def generate_calculations_section(config, calculated_metrics):
    """Generate calculations and formulas section"""
    calculations = config['calculations']
    
    lines = [
        "## 2. Metric Calculations",
        "",
        "### Formulas Used",
        "",
    ]
    
    for calc_name, calc_info in calculations.items():
        lines.append(f"**{calc_name.replace('_', ' ').title()}**")
        lines.append(f"- Formula: `{calc_info['formula']}`")
        lines.append(f"- Description: {calc_info['description']}")
        
        # Add calculated value if available
        if calc_name in calculated_metrics:
            value = calculated_metrics[calc_name]
            if isinstance(value, float):
                lines.append(f"- **Current Value: {value}%**")
            else:
                lines.append(f"- **Current Value: {value}**")
        lines.append("")
    
    return "\n".join(lines)


def generate_quality_checkpoints(config):
    """Generate quality checkpoints section (keeping original implementation)"""
    checkpoints = config['checkpoints']
    
    lines = [
        "## 3. Quality Checkpoints",
        "",
    ]
    
    # Framework Coverage
    lines.append("### Framework Coverage & Maturity")
    lines.append("")
    for checkpoint in checkpoints['framework_coverage']:
        status_icon = "✅" if checkpoint['status'] == "pass" else "⚠️"
        lines.append(f"- {status_icon} **{checkpoint['name']}**")
        if 'current_value' in checkpoint:
            lines.append(f"  - Current: {checkpoint['current_value']}% | "
                        f"Target: {checkpoint['target_value']}%")
        if 'description' in checkpoint:
            lines.append(f"  - {checkpoint['description']}")
    lines.append("")
    
    # Test Case Quality Checklist
    lines.append("### Test Case Quality (Pre-Automation)")
    lines.append("")
    lines.append("All test cases must satisfy:")
    for checkpoint in checkpoints['test_case_quality']:
        required_mark = "✓" if checkpoint['required'] else "○"
        lines.append(f"- [{required_mark}] **{checkpoint['name']}** - {checkpoint['description']}")
    lines.append("")
    
    # Code Quality - POM Structure
    lines.append("### Code Quality - POM Structure")
    lines.append("")
    for checkpoint in checkpoints['code_quality']['pom_structure']:
        required_mark = "✓" if checkpoint['required'] else "○"
        lines.append(f"- [{required_mark}] **{checkpoint['name']}** - {checkpoint['description']}")
    lines.append("")
    
    # Code Quality - Test Class
    lines.append("### Code Quality - Test Class Standards")
    lines.append("")
    for checkpoint in checkpoints['code_quality']['test_class']:
        required_mark = "✓" if checkpoint['required'] else "○"
        lines.append(f"- [{required_mark}] **{checkpoint['name']}** - {checkpoint['description']}")
    lines.append("")
    
    # Selector Quality
    lines.append("### Selector Quality Standards")
    lines.append("")
    for checkpoint in checkpoints['selector_quality']:
        required_mark = "✓" if checkpoint['required'] else "○"
        priority = checkpoint.get('priority', 'medium')
        priority_icon = {"high": "🔴", "medium": "🟡", "low": "🟢"}.get(priority, "🟡")
        lines.append(f"- [{required_mark}] {priority_icon} **{checkpoint['name']}** - {checkpoint['description']}")
    lines.append("")
    
    return "\n".join(lines)


def generate_selector_guidelines(config):
    """Generate selector preference guidelines"""
    selector_pref = config['selector_preference']
    
    lines = [
        "## 4. Selector Strategy",
        "",
        "### Preference Hierarchy",
        "",
    ]
    
    for rank, selector_type in selector_pref['order'].items():
        lines.append(f"{rank}. **{selector_type}**")
    lines.append("")
    
    lines.append("### ✅ Good XPath Examples")
    lines.append("```xpath")
    for example in selector_pref['examples']['good_xpaths']:
        lines.append(example)
    lines.append("```")
    lines.append("")
    
    lines.append("### ❌ Bad XPath Examples")
    lines.append("```xpath")
    for example in selector_pref['examples']['bad_xpaths']:
        lines.append(f"{example}  # {example.split('#')[1].strip() if '#' in example else 'Avoid'}")
    lines.append("```")
    lines.append("")
    
    return "\n".join(lines)


def generate_compliance_section(config):
    """Generate compliance tracking section"""
    compliance = config['compliance']
    
    lines = [
        "## 5. Compliance Status",
        "",
        "| Category | Compliance | Status | Notes |",
        "|----------|-----------|--------|-------|",
    ]
    
    for category in compliance['categories']:
        status_icon = {
            'compliant': '✅',
            'mostly_compliant': '⚠️',
            'non_compliant': '❌'
        }.get(category['status'], '○')
        
        lines.append(
            f"| {category['name']} | "
            f"{category['compliance_percent']}% | "
            f"{status_icon} {category['status'].replace('_', ' ').title()} | "
            f"{category['notes']} |"
        )
    
    lines.append("")
    return "\n".join(lines)


def generate_risk_assessment(config):
    """Generate risk assessment section"""
    risk = config['risk_assessment']
    
    lines = [
        "## 6. Risk Assessment",
        "",
    ]
    
    if risk.get('low_risk'):
        lines.append("### ✅ Low Risk")
        for item in risk['low_risk']:
            lines.append(f"- {item}")
        lines.append("")
    
    if risk.get('medium_risk'):
        lines.append("### ⚠️ Medium Risk")
        for item in risk['medium_risk']:
            lines.append(f"- {item}")
        lines.append("")
    
    if risk.get('high_risk'):
        lines.append("### 🔴 High Risk")
        for item in risk['high_risk']:
            lines.append(f"- {item}")
        lines.append("")
    
    lines.append("### Mitigation Strategies")
    for strategy in risk['mitigation_strategies']:
        lines.append(f"1. {strategy}")
    lines.append("")
    
    return "\n".join(lines)


def generate_recommendations(config):
    """Generate recommendations section"""
    recommendations = config['recommendations']
    
    lines = [
        "## 7. Recommendations",
        "",
    ]
    
    for term, details in recommendations.items():
        term_title = term.replace('_', '-').title()
        lines.append(f"### {term_title} ({details['timeline']})")
        for item in details['items']:
            lines.append(f"- {item}")
        lines.append("")
    
    return "\n".join(lines)


def generate_report(config_file='test-coverage-config.yml', output_file=None, test_directory='src/test/java'):
    """Generate the complete test coverage report with test analysis"""
    
    # Load configuration
    config = load_config(config_file)
    
    # Analyze tests
    print(f"🔍 Analyzing test files in {test_directory}...")
    analyzer = TestAnalyzer(test_directory)
    test_summary = analyzer.get_summary()
    
    # Calculate metrics with actual test counts
    calculated_metrics = calculate_metrics(config, test_summary)
    
    # Generate report sections
    sections = [
        generate_executive_summary(config, calculated_metrics, test_summary),
        generate_test_analysis_section(test_summary, analyzer),
        generate_metrics_overview(config, calculated_metrics),
        generate_calculations_section(config, calculated_metrics),
        generate_quality_checkpoints(config),
        generate_selector_guidelines(config),
        generate_compliance_section(config),
        generate_risk_assessment(config),
        generate_recommendations(config),
    ]
    
    # Add footer
    sections.append("\n---\n")
    sections.append(f"\n**Report Generated:** {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}  ")
    sections.append(f"**Configuration File:** {config_file}  ")
    sections.append(f"**Test Directory:** {test_directory}  ")
    sections.append(f"**Framework Version:** 1.0  \n")
    
    # Combine all sections
    full_report = "\n".join(sections)
    
    # Determine output file
    if output_file is None:
        output_file = config['report_generation']['output_file']
    
    # Write to file
    with open(output_file, 'w') as f:
        f.write(full_report)
    
    print(f"\n✅ Test coverage report generated: {output_file}")
    print(f"\n📊 Key Metrics:")
    print(f"   - Test Files: {test_summary['total_test_files']}")
    print(f"   - Automated Tests: {test_summary['total_test_methods']}")
    print(f"   - Code Compliance: {test_summary['average_compliance_score']}%")
    print(f"   - Automation Coverage: {calculated_metrics['automation_coverage']}%")
    print(f"   - Automation Gap: {calculated_metrics['automation_gap']} tests")
    
    return output_file


if __name__ == "__main__":
    config_file = sys.argv[1] if len(sys.argv) > 1 else 'test-coverage-config.yml'
    output_file = sys.argv[2] if len(sys.argv) > 2 else None
    test_directory = sys.argv[3] if len(sys.argv) > 3 else 'src/test/java'
    
    try:
        generate_report(config_file, output_file, test_directory)
    except FileNotFoundError as e:
        print(f"❌ Error: File not found - {e}")
        sys.exit(1)
    except Exception as e:
        print(f"❌ Error generating report: {e}")
        import traceback
        traceback.print_exc()
        sys.exit(1)
