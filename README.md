# TransactionValidator

Given a file with bank transaction records, create a validation service that reads the file and gives a report with the invalid records.

The file could either be in CSV or JSON format. An example of each is included.

*Input*

Each record in the input file contains the following fields:

**Table 1. Record description**
|  Field                   | Description                               |
| -------------------------| -----------------------------------------:|
| Transaction reference    | A numeric value                           |
| Account number           | An IBAN                                   |
| Start Balance            | The starting balance in Euros             |
| Mutation                 | Either and addition (+) or a deduction (-)|
| Description              | Free text                                 |
| End Balance              | The end balance in Euros                  |


*Output*
There are two validations:


-all transaction references should be unique

-per record the end balance needs to be correct given the start balance and mutation

At the end of the processing, a report needs to be created which will display both the transaction reference and description of each of the failed records.

**to build the project please use this command** 

`./mvnw clean package`
