# Product Context

## Why This Project Exists

### Business Problem

Small to medium businesses need affordable e-commerce solutions but:
- Full-featured platforms are expensive and complex
- Building custom solutions is time-consuming
- Shared infrastructure reduces costs but requires proper tenant isolation
- Fast time-to-market is critical for business success

### Target Users

1. **Business Owners (Tenants)**: Need to quickly set up and manage product catalogs
2. **Store Managers**: Manage day-to-day product visibility and information
3. **Customers**: Browse and view available products
4. **System Administrators**: Manage tenants and system-wide configurations

## What This System Does

### Core Capabilities

#### 1. Multi-Tenant Product Management

- **Create Products**: Name, description, price in BRL, multiple photos
- **Update Products**: Modify any product attribute
- **State Management**:
  - HIDDEN: Default state, not visible to customers
  - SHOWN: Active, visible to customers
  - ARCHIVED: Historical, removed from active catalog
- **Photo Management**: Associate multiple images with products

#### 2. Tenant Isolation

- Each tenant has completely separate product catalogs
- Data never mixes between tenants
- Tenant context enforced at database level
- Independent operations per tenant

#### 3. Authentication & Security

- JWT token-based authentication
- Admin user management
- User identification by username or email
- Password hashing with BCrypt
- Default admin account initialization

#### 4. API Access

- **Management API**: Full CRUD for tenant managers
  - `/api/tenants/{tenantId}/management/products`
- **Customer API**: Read-only, filtered for SHOWN products
  - `/customer/products`

## How It Should Work

### User Workflows

#### Product Lifecycle (Manager)

1. Create product → Starts in HIDDEN state
2. Add photos and details
3. Set state to SHOWN → Customers can see it
4. Update as needed (price, description, photos)
5. Set to HIDDEN → Temporarily remove from customer view
6. Set to ARCHIVED → Permanent removal from active catalog

#### Customer Browsing

1. Request products list
2. Receive only SHOWN products
3. View product details (name, description, price, photos)

#### Tenant Onboarding

1. Admin creates tenant with ID and name
2. Tenant gets isolated database space
3. Managers can start adding products

### Key Behaviors

#### Validation

- Product name: Required, not blank
- Price: Required, positive value, in BRL
- Description: Optional, max 200 characters
- Photos: Optional, URLs to image files
- State: Defaults to HIDDEN if not specified

#### Identity & Audit

- Every entity tracks creator (Author)
- Products, Photos, Users have audit fields
- Identity factory provides current actor context

#### Error Handling

- Resource not found: 404 with structured error response
- Validation failures: 400 with field-level error details
- Server errors: 500 with error representation

## User Experience Goals

### For Managers

- **Fast**: Quick product creation and updates
- **Simple**: Clear state management (show/hide/archive)
- **Reliable**: Consistent API responses, clear errors
- **Isolated**: Never see other tenants' data

### For Customers

- **Clean**: Only see available products (SHOWN state)
- **Fast**: Quick product list retrieval
- **Simple**: Straightforward product information

### For Developers

- **Clear**: Well-documented API with OpenAPI
- **Consistent**: RESTful conventions
- **Testable**: Easy to verify behavior
- **Maintainable**: Clean architecture, good separation

## Domain Model Concepts

### Aggregates

- **Product**: Core aggregate with ProductId, state, details, photos
- **User**: Authentication aggregate with credentials
- **Tenant**: Multi-tenancy root aggregate

### Value Objects

- **ProductId**: Long-based identifier
- **TenantId**: String-based identifier
- **ProductState**: Enumeration (SHOWN, HIDDEN, ARCHIVED)
- **Photo**: URL with audit information
- **Money**: Price in BRL (BigDecimal)
- **Author**: Creator/modifier tracking

### Domain Services

- **UserAuthenticationService**: Password verification
- **ProductManagementUseCases**: Orchestrates product operations
- **TenantContextHolder**: Manages current tenant context

## Business Rules

1. **Product Creation**: Always starts HIDDEN to prevent accidental exposure
2. **Tenant Isolation**: Products belong to exactly one tenant, never shared
3. **State Transitions**: Any state can transition to any other state
4. **Customer Visibility**: Only SHOWN products appear in customer API
5. **Price Format**: Always in Brazilian Reals (BRL), positive values only
6. **Authentication**: JWT required for management operations
7. **Admin Bootstrap**: System creates default admin user on startup

