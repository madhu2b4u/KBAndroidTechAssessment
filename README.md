# KB Android Tech Assessment

This repository contains the solution for the **KB Android Technical Assessment**. The project showcases Android development best practices using **MVVM**, **StateFlow**, **Hilt**, and other modern Android technologies.

## Features

- **Transaction List**: Displays a sorted list of transactions.
- **Balance Calculation**: Computes and shows the user's balance (total sum of transactions).
- **Date Range Filter**: Filters transactions based on selected start and end dates.
- **Reset Filter**: Resets all filters and shows the full transaction list.
- **Date Picker Dialog**: Allows users to pick dates easily using a dialog interface.
- **Dynamic UI Updates**: Reactive UI updates based on user actions and state changes.
- **Dependency Injection**: Powered by Hilt.
- **Coroutines & StateFlow**: For managing asynchronous operations and state management.
- **Clean Architecture**: Follows clean architecture with separation of concerns between UI, domain, and data layers.

## Technologies Used

- **Kotlin**: Primary language for Android development.
- **MVVM Architecture**: Ensures clean separation between UI and business logic.
- **Jetpack Components**: Utilizes Android's modern toolset.
  - `ViewModel`
  - `StateFlow`
  - `LiveData`
  - `DatePicker`
  - `LifecycleObserver`
- **Hilt**: For dependency injection.
- **Coroutines**: For background threading and asynchronous tasks.
- **JUnit & Mockito**: For unit testing and mock testing.

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/KBAndroidTechAssessment.git
2. **Open the project in Android Studio.**
3. **Build the project and run the app on an Android emulator or physical device.**

### How It Works

#### ViewModel
The `TransactionViewModel` manages the business logic and UI state:

* Loads all transactions when initialized.
* Filters transactions based on selected date range.
* Calculates the total balance from the list of transactions.
* Updates the UI state reactively using StateFlow.

#### Repository
The `TransactionRepositoryImpl` class provides transaction data. In this assessment, the data is mocked locally to simulate fetching from an external source (e.g., API or database).

#### UseCase
The `TransactionUseCaseImpl` handles core business logic:

* Filters transactions based on a start date.
* Calculates the total balance by summing transaction amounts.

#### UI Layer
The `MainActivity` observes changes from the `TransactionViewModel` and updates the user interface accordingly. It includes:

* A list view for displaying transactions.
* A button to select a date range using the `DatePickerDialog`.
* Text views for showing the balance and status message.

#### Transactions
Each transaction includes the following attributes:

* **Date:** When the transaction occurred.
* **Description:** A description of the transaction (e.g., "Grocery Store").
* **Amount:** The amount spent or earned (positive values represent income, negative values represent expenses).

#### Date Filtering
Users can select a start and end date to filter transactions:

* The date range filter includes transactions starting from the selected start date to the end date.
* After applying the filter, the balance and status message will update accordingly.
* Users can reset the filter to view all transactions again.

### Project Structure
<img width="627" alt="image" src="https://github.com/user-attachments/assets/da13d96d-650a-4135-acca-30d122f89f1c">

## Future Enhancements

### Data Management
* **Database Integration:** Integrate a database like Room to persist transactions locally, ensuring data persistence even when the app is closed. This will also improve performance for large datasets.
* **API Integration:** Fetch transaction data from an external API to allow for real-time updates and synchronization across multiple devices.

### User Interface
* **UI Improvements:** Implement material design enhancements to improve the app's visual appeal and user experience. Consider using modern components and layouts to create a more intuitive interface.
* **Advanced Filters:** Add additional filters such as filtering by transaction type (e.g., income, expense) or amount range to provide more granular control over data visualization.

By implementing these enhancements, the app can become more robust, feature-rich, and visually appealing, providing a better overall user experience.

