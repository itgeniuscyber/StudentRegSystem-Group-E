# Student Registration System Documentation

## Overview
The Student Registration System is a Java Swing-based desktop application designed to manage student registrations for events or programs. It provides a user-friendly interface for adding, viewing, updating, and deleting student records, along with image management capabilities.

## Project Structure
```
StudentRegSystem/
├── src/main/java/com/genius/studentregsystem/
│   ├── StudentRegSystem.java     # Main registration form
│   └── ParticipantListViewer.java # Participant list viewer
├── images/                       # Directory for student images
└── VUE_Exhibition.accdb          # Microsoft Access database
```

## Main Components

### 1. StudentRegSystem (Main Registration Form)

#### Features
- Student registration with fields for:
  - Registration ID
  - Student Name
  - Course
  - Phone Number
  - Email
  - Image Upload
- Interactive UI elements:
  - Modern styled text fields with focus effects
  - Image preview panel
  - Action buttons with hover effects
- CRUD Operations:
  - Register new students
  - Search existing records
  - Update student information
  - Delete student records
- Form validation and error handling
- Success/Error message dialogs

#### UI Components
- Welcome header with inspirational message
- Styled form fields with focus effects
- Image preview section
- Action buttons (Register, Search, Update, Delete, Clear, Exit)
- Copyright notice

### 2. ParticipantListViewer (List View)

#### Features
- Display all registered participants in a table format
- Search/Filter functionality:
  - Search by specific columns or all columns
  - Real-time filtering
  - Case-insensitive search
- Sortable columns
- Refresh capability
- Modern UI with consistent styling

#### UI Components
- Search panel with column selector
- Data table with styled header
- Action buttons (Refresh, Close)
- Copyright notice

## Database Structure
The system uses a Microsoft Access database (VUE_Exhibition.accdb) with the following structure:

### Participants Table
- Registration_ID (Primary Key)
- Student_Name
- Course
- Phone_Number
- Email
- Image_Path

## User Guide

### Registration Process
1. Launch the application
2. Fill in all required fields
3. Upload a student image (optional)
4. Click "Register" to save the record

### Searching Records
1. Enter the Registration ID
2. Click "Search"
3. Record details will populate the form if found

### Updating Records
1. Search for the record first
2. Modify the desired fields
3. Click "Update" to save changes

### Deleting Records
1. Search for the record first
2. Click "Delete"
3. Confirm deletion when prompted

### Viewing All Records
1. Click "View All" button
2. Use the search/filter feature to find specific records
3. Click column headers to sort
4. Use "Refresh" to update the list

## Technical Implementation

### Key Classes
- `StudentRegSystem`: Main application class handling the registration form and CRUD operations
- `ParticipantListViewer`: Handles the display and filtering of all records

### Design Patterns
- MVC Pattern: Separation of UI, data, and control logic
- Event-Driven Programming: Extensive use of action listeners
- Component-Based Architecture: Modular UI components

### UI Design Principles
- Consistent color scheme and styling
- Responsive feedback through hover effects and focus states
- Clear error and success messages
- Intuitive layout and navigation

## Maintenance and Updates

### Adding New Features
1. Follow existing code structure and patterns
2. Maintain consistent UI styling
3. Add appropriate error handling
4. Update documentation

### Database Modifications
1. Backup database before changes
2. Update corresponding Java code
3. Test all CRUD operations

## System Requirements
- Java Runtime Environment (JRE) 8 or higher
- Microsoft Access Database Engine
- Minimum 4GB RAM
- Windows Operating System

## Credits
© 2024 System Designed by Group E

---
*Last Updated: February 2024*