# Payroll Document Automation System (Spring Boot & Postgres)

## 📌 Overview

The Payroll Document Automation System is a Spring Boot–based backend application designed to automate enterprise payroll processing. It allows HR teams to upload employee and salary data via Excel files, processes data asynchronously using batch and multi-threading concepts, and generates monthly payslips as PDF documents using JasperReports.

This project simulates real-world enterprise backend systems involving file processing, schedulers, versioned data handling, and document generation.

---

## 🏗️ System Features

### 1️⃣ Employee Data Upload

* HR can upload **Excel files only** containing employee details
* File is validated and parsed
* Employee data is persisted into the database
* Uploaded file is archived in the **Inbound Archive** folder

### 2️⃣ Salary Data Upload with Versioning

* HR uploads Excel file with salary details mapped by Employee ID
* If salary already exists:

  * Old record marked as `isCurrent = false`
  * New salary record saved with `isCurrent = true`
* Ensures historical salary tracking

### 3️⃣ Asynchronous & Multi-threaded Processing

* Excel file records are processed asynchronously
* Multiple records handled in parallel using thread pools
* Improves performance for large files

### 4️⃣ File Audit & Tracking

* All uploaded and generated files are logged in `FileStores` table
* Tracks:

  * File name
  * Source 
  * Storage path

### 5️⃣ Payslip PDF Generation (On-demand)

* REST API accepts Employee ID
* Fetches current salary (`isCurrent = true`)
* Generates PDF payslip using **JasperReports (.jrxml)**

### 6️⃣ Scheduled Payslip Generation

* Scheduler runs based on configurable **cron expression**
* Picks all current salary records
* Generates payslips in **batches**
* Stores generated PDFs in file storage

### 7️⃣ Payslip Download API

* User can download payslip by providing:

  * Employee ID
  * Pay period
  * Year
* System retrieves file from storage and returns it via API

---

## 🧩 Core Modules

* Employee Management Module
* Salary Processing & Versioning Module
* File Upload & Validation Module
* Asynchronous Batch Processing Module
* Scheduler & Cron Job Module
* PDF Generation (JasperReports)
* File Storage & Archival Module

---

## 🛠️ Tech Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Database:**  PostgreSQL
* **ORM:** Spring Data JPA / Hibernate
* **Reporting:** JasperReports (.jrxml)
* **File Processing:** Apache POI
* **Scheduling:** Spring Scheduler (@Scheduled)
* **Concurrency:** ExecutorService, Multi-threading

---

## 🧠 Key Concepts Demonstrated

* Asynchronous processing
* Batch job execution
* Multi-threading
* File upload/download
* Scheduler-based automation
* Versioned database records
* Real-world enterprise backend design

---

## 🚀 Future Enhancements

* Role-based access control (HR / Employee)
* Error report generation for invalid Excel rows
* Retry mechanism for failed batch jobs
* Cloud storage integration (AWS S3)
* Notification service (Email)

---

## 👤 Author
**Sailesh Kathir A**
