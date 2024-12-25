# Utility Classes Documentation

This document provides details about utility interfaces and classes used in the `org.cognitivefinder.microservice.utils` package. These utilities are designed to assist with the mapping of entities, data transfer objects (DTOs), request handling, and pagination in microservice architectures.

---

## IMapper Interface

### Purpose
The `IMapper` interface defines methods for transforming between entities, DTOs, and request objects (create and update requests).

### Methods

- **`DTO toDTO(E entity)`**  
  Converts an entity object to a corresponding DTO.

- **`E toEntity(CREQ createRequest)`**  
  Converts a create request to an entity object.

- **`E toEntity(UREQ updateRequest, E entity)`**  
  Updates an existing entity with the values from an update request.

### Type Parameters
- `E` - The entity type.
- `DTO` - The DTO type.
- `CREQ` - The create request type.
- `UREQ` - The update request type.

---

## IRESTController Interface

### Purpose
The `IRESTController` interface defines methods for handling HTTP requests in a RESTful controller, specifically for fetching, creating, updating, and deleting resources.

### Methods

- **`ResponseEntity<DTO> fetchById(ID id)`**  
  Fetches a resource by its ID.

- **`ResponseEntity<List<DTO>> fetchAll()`**  
  Fetches all resources.

- **`ResponseEntity<DTO> create(CREQ req)`**  
  Creates a new resource based on the provided request.

- **`ResponseEntity<DTO> update(UREQ req, ID id)`**  
  Updates an existing resource with the provided request and ID.

- **`ResponseEntity<Void> delete(ID id)`**  
  Deletes a resource by its ID.

- **`ResponseEntity<OwnPageRES<DTO>> fetchAll(Integer page, Integer size, String sortBy, String orderBy, String search, List<String> filter)`**  
  Fetches a paginated list of resources with optional sorting, ordering, search, and filtering.

### Type Parameters
- `DTO` - The DTO type.
- `CREQ` - The create request type.
- `UREQ` - The update request type.
- `ID` - The ID type for the resource.

---

## IService Interface

### Purpose
The `IService` interface defines core service methods for creating, updating, finding, and deleting resources, with additional support for pagination.

### Methods

- **`DTO create(CREQ req) throws BusinessException`**  
  Creates a new resource from the provided request.

- **`DTO update(ID id, UREQ req) throws BusinessException`**  
  Updates an existing resource by its ID with the provided update request.

- **`DTO findById(ID id) throws BusinessException`**  
  Finds a resource by its ID.

- **`void delete(ID id) throws BusinessException`**  
  Deletes a resource by its ID.

- **`List<DTO> findAll()`**  
  Fetches all resources.

- **`OwnPageRES<DTO> findAll(Pageable pageable)`**  
  Fetches a paginated list of resources.

### Type Parameters
- `DTO` - The DTO type.
- `CREQ` - The create request type.
- `UREQ` - The update request type.
- `ID` - The ID type for the resource.

---

## OwnPageRES Class

### Purpose
`OwnPageRES` is a generic class used to represent a paginated response, containing metadata such as total elements, total pages, and the content of the current page.

### Fields
- `content` - A list of resources for the current page.
- `totalPages` - The total number of pages available.
- `totalElements` - The total number of resources available.
- `size` - The number of resources per page.
- `currentPage` - The current page number (1-based).
- `sortBy` - The field used for sorting the results.
- `orderBy` - The order of sorting (ASC/DESC).

### Type Parameters
- `T` - The type of resources in the paginated list.

---

## PageMapper Class

### Purpose
The `PageMapper` class provides a utility method to convert a `Page` object from Spring Data into an `OwnPageRES` object, applying a custom mapping function for the content.

### Methods

- **`public static <T, R> OwnPageRES<R> toMyPageRES(Page<T> page, Pageable pageable, Function<T, R> mapper)`**  
  Converts a `Page` of type `T` to an `OwnPageRES` of type `R`, applying the specified mapping function.

### Parameters
- `page` - The `Page` object to be converted.
- `pageable` - The `Pageable` object containing pagination information.
- `mapper` - A function to map each element from type `T` to type `R`.

### Type Parameters
- `T` - The type of the original entity in the page.
- `R` - The type of the result after applying the mapping function.

---

## Conclusion

These utility interfaces and classes provide a robust foundation for handling common CRUD operations, pagination, and entity transformations across various microservices. By using these utilities, developers can ensure consistency and efficiency in their microservice architectures.