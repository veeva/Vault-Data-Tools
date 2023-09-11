# Vault Data Tools

When a Sandbox Vault exceeds its <a href="https://platform.veevavault.help/en/gr/48988/#sandbox-sizes--limits">data limits</a>, Vault admins must delete object records or document versions to clear the block. Vault Data Tools is a command-line tool that simplifies this process, allowing users to quickly count and then delete Sandbox data in bulk. And because it’s open-source, developers can extend and modify the tool to suit custom use cases.

## Setup

This tool is distributed as a single JAR file, and does not require installation. Simply navigate to the link below and
click on the "Download" button. From there, the jar file can be run from a command line console.

Download the
latest <a href="https://gitlab.veevadev.com/veevavaultdevsupport/vault-data-tools/-/blob/main/vault-data-tools-23.2.0.jar">
vault-data-tools-23.2.0.jar</a>.

## Quick Start

### Count Data

Open a command line, and navigate to the folder where your jar file is. To Count Data, run the jar file with the desired
command line inputs. The basic structure of a command using Vault Data Tools is:

```
java -jar {jarFile} -datatype {datatype} -action {actionName} -input {filepath} -vaultDNS {vaultDNS} -username {username} -password {"password"}
```

### Commands For Count Data

| Command    | Parameter   | Example                                 | Description                                                                                                                                                                                                                                                                                                                          |
|------------|-------------|-----------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| -action    | COUNT       | ```-action COUNT```                     | Set the tool to count data                                                                                                                                                                                                                                                                                                           |
| -datatype  | OBJECTS     | ```-datatype OBJECTS```                 | Used to count all object record data in the specified Vault                                                                                                                                                                                                                                                                          |
|            | DOCUMENTS   | ```-datatype DOCUMENTS```               | Used to count all documents in the specified Vault                                                                                                                                                                                                                                                                                   |
|            | ALL         | ```-datatype ALL```                     | Used to count all data (object records and documents) from a specified Vault                                                                                                                                                                                                                                                         |
| -input     | {.csv file} | ```-input ./objects-to-count.csv```     | Optional path to location of input file containing a list of specific objects to count when using the ```-datatype OBJECTS``` command. Find an example input file here: <a href="https://gitlab.veevadev.com/veevavaultdevsupport/vault-data-tools/-/blob/main/objects-to-count.csv">objects-to-count.csv</a>                        |
|            | {.csv file} | ```-input ./documents-to-count.csv```   | Optional path to location of input file containing a list of specific document types to count when using the ```-datatype DOCUMENTS``` command. Find an example input file here: <a href="https://gitlab.veevadev.com/veevavaultdevsupport/vault-data-tools/-/blob/main/document-types-to-count.csv">document-types-to-count.csv</a> |
| -vaultDNS  | {vault DNS} | ```-vaultDNS cholecap.veevavault.com``` | Vault DNS to count data from (must be a Sandbox)                                                                                                                                                                                                                                                                                     |
| -username  | {username}  | ```-username {username}```              | For Authenticating to a Vault                                                                                                                                                                                                                                                                                                        |
| -password  | {password}  | ```-password {"password"}```            | For Authenticating to a Vault                                                                                                                                                                                                                                                                                                        |
| -sessionId | {sessionId} | ```-sessionId {sessionId}```            | For Authenticating to a Vault                                                                                                                                                                                                                                                                                                        |
| -exclude   | {source}    | ```-exclude SYSTEM,STANDARD,CUSTOM```   | Optional comma-delimited list to exclude System, Standard, or Custom Objects from being deleted when using ```-datatype OBJECTS```. Expected values: SYSTEM, STANDARD, OR CUSTOM (must be a comma-delimited-list without spaces).                                                                                                    |

#### Example Commands

1. Count All Data

```
java -jar vault-data-tools-23.2.0.jar -datatype ALL -action COUNT -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

2. Count All Objects

```
java -jar vault-data-tools-23.2.0.jar -datatype OBJECTS -action COUNT -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

3. Count All Documents

```
java -jar vault-data-tools-23.2.0.jar -datatype DOCUMENTS -action COUNT -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

4. Count Specific Objects

```
java -jar vault-data-tools-23.2.0.jar -datatype DOCUMENTS -action COUNT -input ./objects-to-count.csv -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

5. Count All Custom Objects (Exclude System/Standard)

```
java -jar vault-data-tools-23.2.0.jar -datatype OBJECTS -action COUNT -exclude SYSTEM,STANDARD -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

<br />

### Delete Data

Open a command line, and navigate to the folder where your jar file is. To Delete Data, run the jar file with the
desired command line inputs. The basic structure of a command using Vault Data Tools is:

```
java -jar {jarFile} -datatype {datatype} -action {actionName} -input {filepath} -vaultDNS {vaultDNS} -username {username} -password {"password"}
```

### Commands For Delete Data

| Command    | Parameter   | Example                                 | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
|------------|-------------|-----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| -action    | DELETE      | ```-action DELETE```                    | Set the tool to delete data                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| -datatype  | OBJECTS     | ```-datatype OBJECTS```                 | Used to delete all object record data in the specified Vault                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
|            | DOCUMENTS   | ```-datatype DOCUMENTS```               | Used to delete all documents in the specified Vault                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
|            | ALL         | ```-datatype ALL```                     | Used to delete all data (object records and documents) from a specified Vault                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| -input     | {.csv file} | ```-input ./objects-to-delete.csv```    | Optional path to location of input file containing a list of specific objects to delete when using the ```-datatype OBJECTS``` command. Find an example input file here: <a href="https://gitlab.veevadev.com/veevavaultdevsupport/vault-data-tools/-/blob/main/objects-to-delete.csv">objects-to-delete.csv</a>. To delete all records for a specific object, provide the object name in the first column. To optionally delete only specific records within that object, provide a unique idParam and idParamValue to identify those records. |
|            | {.csv file} | ```-input ./documents-to-delete.csv```  | Optional path to location of input file containing a list of specific document types to delete when using the ```-datatype DOCUMENTS``` command. Find an example input file here: <a href="https://gitlab.veevadev.com/veevavaultdevsupport/vault-data-tools/-/blob/main/document-types-to-delete.csv">document-types-to-delete.csv</a>                                                                                                                                                                                                         |
| -vaultDNS  | {vault DNS} | ```-vaultDNS cholecap.veevavault.com``` | Vault DNS to delete data from (must be a Sandbox)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| -username  | {username}  | ```-username {username}```              | For Authenticating to a Vault                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| -password  | {password}  | ```-password "{password}"```            | For Authenticating to a Vault                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| -sessionId | {sessionId} | ```-sessionId {sessionId}```            | For Authenticating to a Vault                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| -exclude   | {source}    | ```-exclude SYSTEM,STANDARD,CUSTOM```   | Optional comma-delimited list to exclude System, Standard, or Custom Objects from being deleted when using ```-datatype OBJECTS```. Expected values: SYSTEM, STANDARD, OR CUSTOM (must be a comma-delimited-list without spaces).                                                                                                                                                                                                                                                                                                               |                                                                                                                                                                                                                                                                                                                                                                                                                                       |

<br />

#### Example Commands

1. Delete All Data

```
java -jar vault-data-tools-23.2.0.jar -datatype ALL -action DELETE -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

2. Delete All Objects

```
java -jar vault-data-tools-23.2.0.jar -datatype OBJECTS -action DELETE -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

3. Delete All Documents

```
java -jar vault-data-tools-23.2.0.jar -datatype DOCUMENTS -action DELETE -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

4. Delete Specific Objects

```
java -jar vault-data-tools-23.2.0.jar -datatype OBJECTS -action DELETE -input ./objects-to-delete.csv -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

5. Delete Specific Document Types

```
java -jar vault-data-tools-23.2.0.jar -datatype DOCUMENTS -action DELETE -input ./documents-to-delete.csv -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```

6. Delete All Custom Objects (Exclude System/Standard)

```
java -jar vault-data-tools-23.2.0.jar -datatype OBJECTS -action DELETE -exclude SYSTEM,STANDARD -vaultDNS cholecap.veevavault.com -username my-username@cholecap.veevavault.com -password "my-password"
```