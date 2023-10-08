# operation-core

Status of Last Deployment:<br>
[![Build Status](https://github.com/serezakorotaev/operation-core/workflows/CI-operation-core/badge.svg)](https://github.com/serezakorotaev/operation-core/actions/workflows/maven.yml)<br>

From maven central <br>
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.sergkorot/operation-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ru.sergkorot.dynamic/operation-core)

```xml
<dependency>
    <groupId>ru.sergkorot.dynamic</groupId>
    <artifactId>operation-core</artifactId>
    <version>x.x.x</version>
</dependency>
```

# Content list

[About library](#1-about-library)


[OperationService](#2-operationservice)

[Supported operations](#3-supported-operations)
- [IN](#in)
- [NOT IN](#notin)
- [LIKE](#like)
- [EQUAL](#equal)
- [NOT EQUAL](#notequal)
- [IS NULL](#isnull)
- [LESS THAN](#lessthan)
- [GREATER THAN](#greaterthan)
- [LESS THAN OR EQUALS](#lessthanorequals)
- [GREATER THAN OR EQUALS](#greaterthanorequals)
- [CONTAINS](#contains)

[Models for searching and paging](#4-models-for-searching-and-paging)
- [BaseSearchParam](#basesearchparam)
- [ComplexSearchParam](#complexsearchparam)
- [PageAttribute](#pageattribute)

[Request examples](#5-request-examples)

[Other useful classes](#6-other-useful-classes)
- [Utils](#utils)
- [RegexpUtils](#regexputils)
- [SortUtils](#sortutils)

## 1. [About Library](#content-list)

Library has base interfaces for creating implementations which build more flexible requests into the different databases.
Using a few library's methods you will have opportunity to build simple and complex requests,
including conjunction and disjunction with different operations such as *equals*, *like*, *lessThan*,
*in* and others.


## 2. [OperationService](#content-list)

This is an interface for building requests into the databases with different parameters and glue option.
At the moment are existed two implementation of this interface: `SpecificationOperationService`
and `CriteriaOperationService`.
`SpecificationOperationService` implementation is used to the [spring-boot-operation-starter](https://github.com/serezakorotaev/spring-boot-operation-starter)
`CriteriaOperationService` implementation is used to the [spring-boot-operation-mongodb-starter](https://github.com/serezakorotaev/spring-boot-operation-mongodb-starter)

OperationService is tree methods for realizing:

- a. `T buildBaseByParams(List<BaseSearchParam> baseSearchParams, GlueOperation glue)`

Method for building base request using search parameters (baseSearchParams)
and condition for linking parameters (glue). `BaseSearchParam` and `GlueOperation` classes will be described
below.

- b. `T buildComplexByParams(List<ComplexSearchParam> complexSearchParams, GlueOperation externalGlue)`

Method for building complex request using structure with base search parameters (complexSearchParams)
and condition for linking base parameters with each other (externalGlue). `ComplexSearchParam` class will be described
below.

- c. `default T buildOperation(BaseSearchParam param, OperationProvider<T> operationProvider) {
  return OperationType.of(param.getOperation()).getOperation(operationProvider).buildOperation(param);
  }`

Default method for build resulting Operation with some condition. There are Specification, Criteria or something like that.

## 3. [Supported operations](#content-list)

In library has different operations for searching. User need to select operation in field "operation" and it will be
processed.
All operations are in `OperationType` and interface for their implementation in `OperationProvider<R>`.

### [IN](#content-list)

IN operation is used for searching records by specified elements

    Example:
            {
            "name": "name",
            "value": "John,Max",
            "operation": "in"
            }

In example above, predicate will be built with condition (find all by name in (John,Max))

### [NOT_IN](#content-list)

NOT_IN operation is used for searching records without specified elements

    Example:
            {
            "name": "name",
            "value": "John,Max",
            "operation": "notIn"
            }

In example above, predicate will be built with condition (find all by name not in (John,Max))

### [LIKE](#content-list)

Like operation is used for searching records where contains specified string

    Example:
            {
            "name": "name",
            "value": "Jo",
            "operation": "like"
            }

In example above, predicate will be built with condition (find all by name like (%Jo%))

### [EQUAL](#content-list)

Equal operation is used for searching records by strict match

    Example:
            {
            "name": "varsion",
            "value": 1,
            "operation": "eq"
            }

In example above, predicate will be built with condition (find all where version = 1)

### [NOT_EQUAL](#content-list)

Not equal operation is used for searching records where elements haven't specified value

    Example:
            {
            "name": "version",
            "value": 1,
            "operation": "notEq"
            }

In example above, predicate will be built with condition (find all where version != 1)

### [IS_NULL](#content-list)

Is null operation is used for searching records where elements haven't null value

    Example:
            {
            "name": "version",
            "operation": "isNull"
            }

In example above, predicate will be built with condition (find all where version is null)

### [LESS_THAN](#content-list)

Less than operation is used for searching comparing records where elements less than specified

    Example:
            {
            "name": "age",
            "value": 20,
            "operation": "lt"
            }

In example above, predicate will be built with condition (find all where age < 20)

### [GREATER_THAN](#content-list)

Greater than operation is used for searching comparing records where elements greater than specified

    Example:
            {
            "name": "age",
            "value": 20,
            "operation": "gt"
            }

In example above, predicate will be built with condition (find all where age > 20)

### [LESS_THAN_OR_EQUALS](#content-list)

Less than operation is used for searching comparing records where elements less than or equal specified

    Example:
            {
            "name": "age",
            "value": 21,
            "operation": "le"
            }

In example above, predicate will be built with condition (find all where age <= 21)

### [GREATER_THAN_OR_EQUALS](#content-list)

Less than operation is used for searching comparing records where elements greater than or equal specified

    Example:
            {
            "name": "age",
            "value": 21,
            "operation": "ge"
            }

In example above, predicate will be built with condition (find all where age >= 21)

### [CONTAINS](#content-list)

Contains operation is used for searching records where elements contains specified values (operation also is used for
jsonb fields)

    Example:
          {
          "name": "description",
          "value": "a,is",
          "operation": "contains"
          }

In example above, predicate will be built with condition (find all where description contains (a and is strings))

## 4. [Models for searching and paging](#content-list)

For searching and paging are three base models - `BaseSearchParam`, `ComplexSearchParam` and `PageAttribute`.
And Also shell for them are `CommonOperationShell` and `MultipleOperationShell`.

### [`BaseSearchParam`](#content-list)

  ```
  class BaseSearchParam {
    private String name;
    private Object value;
    private String operation;
  }
  ```

Is base class for searching. it has

- `name` - field's name which need to search
- `value` - value which need to find
- `operation` - operation name which describe above

```
    Example:
        {
            "name": "name",
            "value": "Ian.Hessel",
            "operation": "eq"
        }
```

### [`ComplexSearchParam`](#content-list)

```
  class ComplexSearchParam {
      List<BaseSearchParam> baseSearchParams;
      GlueOperation internalGlue = AND;
  }
```

is more complex class for searching. It has

- list `baseSearchParams` which has fields and operations for searching
- `internalGlue` which allows you to glue all given conditions. AND/OR value

```
    Example:
    {
      "baseSearchParams": [
          {
              "name": "name",
              "value": "Ian.Hessel",
              "operation": "eq"
          },
          {
              "name": "description",
              "value": "withdrawal",
              "operation": "eq"
          }
      ],
      "glue" : "OR"
    }
```

### [`PageAttribute`](#content-list)

  ```
  class PageAttribute {
      private Integer limit;
      private Integer offset;
      private String sortBy;
  }
  ```

Is used for building page settings for paging

- `limit` Number of list items to return
- `offset` Shift relative to the beginning of the list
- `sortBy` Parameter for sorting. Perhaps multiple sorting through comma (name,-surname),
  which means name ASC and surname DESC

## 5. [Request examples](#content-list)

base search:

  ```
  {
    "baseSearchParams": [
        {
            "name": "name",
            "value": "Ian.Hessel",
            "operation": "eq"
        },
        {
            "name": "description",
            "value": "withdrawal",
            "operation": "eq"
        }
    ],
    "glue" : "OR",
    "pageAttribute" : {
        "limit" : 10,
        "offset" : 0,
        "sortBy" : "name"
    }
  }
  ```

In example above, is used `CommonOperationShell` for simple request

  ```
(
Find all where name.equals("Ian.Hessel") or description.equals("withdrawal")
with limit=10 and offset=0 and sort by name ASC
)
  ```

complex search:

  ```
  {
    "search": [
        {
            "baseSearchParams": [
                {
                    "name": "name",
                    "value": "Rhett14",
                    "operation": "eq"
                },
                {
                    "name": "version",
                    "value": "0,2",
                    "operation": "in"
                }
            ],
            "internalGlue": "AND"
        },
        {
            "baseSearchParams": [
                {
                    "name": "name",
                    "value": "Reggie19",
                    "operation": "eq"
                },
                {
                    "name": "description",
                    "value": "Up-sized",
                    "operation": "like"
                }
            ],
            "internalGlue": "AND"
        }
    ],
    "externalGlue": "OR",
    "pageAttribute": {
        "limit": 10
    }
  }
  ```

In example above, is used `MultipleOperationShell` for complex request

  ```
(
find all where (name.equals("Rhett14") and version in(0, 2)) or (name.equals("Reggie19") and description like ("%Up-sized%"))
)
  ```

## 6. [Other useful classes](#content-list)

### [Utils](#content-list)

#### [`RegexpUtils`](#content-list)
- `RegexpUtils.transformToArrayFieldsNames(String fieldsNames)`

Util is used for transforming string by pattern to list strings with strings for further paging

  ```
  Example:
  name,-surname,version -> ["name", "-surname", "version]
  
  ```

#### [`SortUtils`](#content-list)

- `SortUtils.makeSortOrders(final Collection<String> validNames, final String sortValues)`

Util is used for transforming string by pattern inside (uses RegexpUtils) to list
org.springframework.data.domain.Sort.Order class
and checking by validNames if it can build Sort.Order by these sortValue names

- `SortUtils.makeSort(final Collection<String> validNames, final String sortValues)`

Util is used for transforming string by pattern inside (uses RegexpUtils) to list
org.springframework.data.domain.Sort class
and checking by validNames if it can build Sort.Order by these sortValue names

  ```
  Example:
  validNames : name,-surname
  sortValues: name,-surname,version -> Not found parameter with name: version
  
  ```