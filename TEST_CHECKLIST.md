# Selenium Test Automation Framework – QA Checklist (POM)

This document contains the main checkpoints for maintaining test quality, test coverage, and XPath standards in the Selenium Java POM framework.

---

##  1. Framework Coverage & Maturity

### **Test Coverage Summary**
| Category | Value |
|---------|-------|
| Total test cases |  10|
| Automated test cases |  8|
| Automation Coverage % |  80%|
| Last updated |  11/12/2025|

**Formula:**  
Automation Coverage % = `(Automated Tests / Total Tests) × 100`

---

##  2. Test Case Checklist (Before Adding Test to Automation)
- [ ] Test case is valid and approved by QA Lead  
- [ ] Preconditions documented  
- [ ] Test data defined  
- [ ] Positive + negative cases identified  
- [ ] Edge cases listed  
- [ ] Selector details (IDs/XPaths) checked and added  
- [ ] Ability to automate verified  

---

##  3. Automation Checklist (Before Merging Code)

### **POM Structure**
- [ ] Page class created inside `/src/main/java/pages/`  
- [ ] Each locator defined at top of class  
- [ ] Methods written with meaningful names  
- [ ] Page object contains *zero* assertions  
- [ ] No test logic inside page classes  

### **Test Class**
- [ ] Test extends `BaseTest`  
- [ ] Assertions only in test class  
- [ ] Proper waits used instead of Thread.sleep  
- [ ] No hard-coded data  
- [ ] Uses config file for URLs / credentials  
- [ ] Screenshots captured for failures  

---

##  4. Selector & XPath Checklist

### **Preferred Locators**
1. **ID**  
2. **Name**  
3. **CSS Selector**  
4. **XPath** (only when needed)

### **XPath Rules**
- [ ] No absolute XPaths like `/html/body/...`
- [ ] Use meaningful, stable attributes  
- [ ] Avoid indexes like `(//button)[3]`  
- [ ] Use functions like `contains()`, `starts-with()` only when required  
- [ ] Prefer text-based XPath for unique UI elements  

### **Good XPath Examples**
```xpath
//input[@id='email']
//*[@data-testid='login-btn']
//button[contains(text(),'Submit')]