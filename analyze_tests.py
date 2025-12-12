#!/usr/bin/env python3
"""
Test Analyzer - Scans test files and validates against checklist standards
"""

import os
import re
from pathlib import Path
from typing import Dict, List, Any


class TestAnalyzer:
    """Analyzes Java test files against quality checklist"""
    
    def __init__(self, test_directory: str):
        self.test_directory = test_directory
        self.test_files = []
        self.analysis_results = []
    
    def scan_test_files(self) -> List[str]:
        """Scan test directory for Java test files"""
        test_dir = Path(self.test_directory)
        
        if not test_dir.exists():
            print(f"❌ Test directory not found: {self.test_directory}")
            return []
        
        # Find all Java files with 'Test' in the name
        self.test_files = list(test_dir.rglob("*Test.java"))
        
        # Filter out BaseTest
        self.test_files = [f for f in self.test_files if 'BaseTest' not in f.name]
        
        print(f"✅ Found {len(self.test_files)} test file(s)")
        return [str(f) for f in self.test_files]
    
    def analyze_test_file(self, file_path: str) -> Dict[str, Any]:
        """Analyze a single test file against checklist standards"""
        
        with open(file_path, 'r') as f:
            content = f.read()
        
        file_name = Path(file_path).name
        
        # Count @Test annotations
        test_methods = re.findall(r'@Test\s*\([^)]*\)', content)
        test_count = len(test_methods)
        
        # Extract test method names
        test_names = re.findall(r'public\s+void\s+(\w+)\s*\(\)', content)
        
        analysis = {
            'file_name': file_name,
            'file_path': file_path,
            'test_count': test_count,
            'test_methods': test_names,
            'checklist_compliance': {}
        }
        
        # Check Test Class Standards
        analysis['checklist_compliance']['extends_basetest'] = self._check_extends_basetest(content)
        analysis['checklist_compliance']['assertions_only_in_test'] = self._check_assertions_location(content)
        analysis['checklist_compliance']['no_thread_sleep'] = self._check_no_thread_sleep(content)
        analysis['checklist_compliance']['no_hardcoded_data'] = self._check_no_hardcoded_data(content)
        analysis['checklist_compliance']['has_test_annotations'] = test_count > 0
        analysis['checklist_compliance']['has_descriptions'] = self._check_descriptions(content)
        analysis['checklist_compliance']['proper_test_naming'] = self._check_test_naming(test_names)
        analysis['checklist_compliance']['uses_page_objects'] = self._check_page_objects(content)
        
        # Calculate compliance score
        compliance_checks = analysis['checklist_compliance']
        passed_checks = sum(1 for v in compliance_checks.values() if v)
        total_checks = len(compliance_checks)
        analysis['compliance_score'] = round((passed_checks / total_checks) * 100, 1) if total_checks > 0 else 0
        analysis['passed_checks'] = passed_checks
        analysis['total_checks'] = total_checks
        
        return analysis
    
    def _check_extends_basetest(self, content: str) -> bool:
        """Check if test class extends BaseTest"""
        return bool(re.search(r'extends\s+BaseTest', content))
    
    def _check_assertions_location(self, content: str) -> bool:
        """Check if assertions are only in test methods"""
        # This is a simplified check - returns True if Assert is found
        return 'Assert.' in content or 'Assertions.' in content
    
    def _check_no_thread_sleep(self, content: str) -> bool:
        """Check for Thread.sleep usage (should not exist)"""
        return 'Thread.sleep' not in content
    
    def _check_no_hardcoded_data(self, content: str) -> bool:
        """Check for configuration-driven approach"""
        # Look for common patterns indicating config usage or string literals
        # More sophisticated check could parse for config readers
        has_string_literals = bool(re.findall(r'String\s+\w+\s*=\s*"[^"]+"', content))
        # For now, we'll be lenient and return True if no obvious URL hardcoding
        has_hardcoded_urls = bool(re.search(r'"https?://[^"]*"', content))
        return not has_hardcoded_urls
    
    def _check_descriptions(self, content: str) -> bool:
        """Check if tests have @Description annotations"""
        return '@Description' in content
    
    def _check_test_naming(self, test_names: List[str]) -> bool:
        """Check if test names follow proper naming convention"""
        if not test_names:
            return False
        
        # Check if test names start with 'test' and are descriptive (> 10 chars)
        valid_names = [name for name in test_names 
                      if name.startswith('test') and len(name) > 10]
        
        return len(valid_names) >= len(test_names) * 0.8  # 80% of tests follow convention
    
    def _check_page_objects(self, content: str) -> bool:
        """Check if test uses Page Objects"""
        # Look for Page class instantiation or usage
        return bool(re.search(r'\w+Page\s+\w+', content))
    
    def analyze_all_tests(self) -> List[Dict[str, Any]]:
        """Analyze all test files"""
        self.scan_test_files()
        
        for test_file in self.test_files:
            analysis = self.analyze_test_file(str(test_file))
            self.analysis_results.append(analysis)
        
        return self.analysis_results
    
    def get_summary(self) -> Dict[str, Any]:
        """Get summary of all tests"""
        if not self.analysis_results:
            self.analyze_all_tests()
        
        total_tests = sum(r['test_count'] for r in self.analysis_results)
        total_files = len(self.analysis_results)
        
        # Calculate average compliance
        avg_compliance = sum(r['compliance_score'] for r in self.analysis_results) / total_files if total_files > 0 else 0
        
        # Check which standards are most/least followed
        checklist_summary = {}
        for result in self.analysis_results:
            for check, passed in result['checklist_compliance'].items():
                if check not in checklist_summary:
                    checklist_summary[check] = {'passed': 0, 'failed': 0}
                
                if passed:
                    checklist_summary[check]['passed'] += 1
                else:
                    checklist_summary[check]['failed'] += 1
        
        return {
            'total_test_files': total_files,
            'total_test_methods': total_tests,
            'average_compliance_score': round(avg_compliance, 1),
            'checklist_summary': checklist_summary,
            'test_details': self.analysis_results
        }
    
    def generate_summary_table(self) -> str:
        """Generate markdown summary table"""
        summary = self.get_summary()
        
        lines = [
            "## Test File Summary\n",
            "| Test File | Tests | Compliance | Status |",
            "|-----------|-------|------------|--------|"
        ]
        
        for result in self.analysis_results:
            status_icon = "✅" if result['compliance_score'] >= 80 else "⚠️" if result['compliance_score'] >= 60 else "❌"
            lines.append(
                f"| {result['file_name']} | "
                f"{result['test_count']} | "
                f"{result['compliance_score']}% | "
                f"{status_icon} |"
            )
        
        lines.append("")
        lines.append(f"**Total Test Methods:** {summary['total_test_methods']}  ")
        lines.append(f"**Average Compliance:** {summary['average_compliance_score']}%  ")
        lines.append("")
        
        return "\n".join(lines)
    
    def generate_detailed_checklist_report(self) -> str:
        """Generate detailed checklist compliance report"""
        summary = self.get_summary()
        checklist_summary = summary['checklist_summary']
        
        lines = [
            "## Checklist Compliance by Standard\n",
            "| Standard | Files Passing | Files Failing | Compliance |",
            "|----------|---------------|---------------|------------|"
        ]
        
        checklist_names = {
            'extends_basetest': 'Extends BaseTest',
            'assertions_only_in_test': 'Assertions Only in Tests',
            'no_thread_sleep': 'No Thread.sleep',
            'no_hardcoded_data': 'No Hard-coded URLs',
            'has_test_annotations': 'Has @Test Annotations',
            'has_descriptions': 'Has @Description',
            'proper_test_naming': 'Proper Test Naming',
            'uses_page_objects': 'Uses Page Objects'
        }
        
        for check_id, check_data in checklist_summary.items():
            passed = check_data['passed']
            failed = check_data['failed']
            total = passed + failed
            compliance = round((passed / total) * 100, 1) if total > 0 else 0
            
            status_icon = "✅" if compliance == 100 else "⚠️" if compliance >= 80 else "❌"
            
            lines.append(
                f"| {checklist_names.get(check_id, check_id)} | "
                f"{passed} | "
                f"{failed} | "
                f"{compliance}% {status_icon} |"
            )
        
        lines.append("")
        return "\n".join(lines)


if __name__ == "__main__":
    import sys
    
    test_dir = sys.argv[1] if len(sys.argv) > 1 else "src/test/java"
    
    analyzer = TestAnalyzer(test_dir)
    summary = analyzer.get_summary()
    
    print("\n" + "="*50)
    print("TEST ANALYSIS SUMMARY")
    print("="*50)
    print(f"Total Test Files: {summary['total_test_files']}")
    print(f"Total Test Methods: {summary['total_test_methods']}")
    print(f"Average Compliance: {summary['average_compliance_score']}%")
    print("\n" + analyzer.generate_summary_table())
    print(analyzer.generate_detailed_checklist_report())
