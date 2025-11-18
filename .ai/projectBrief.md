# Project Brief: Kwik Ecommerce

## Project Identity

**Name:** Kwik Ecommerce  
**Type:** Multi-tenant E-commerce Platform API  
**Tech Stack:** Java 21, Quarkus Framework  
**Repository:** kwik-ecommerce-quarkus

## Core Purpose

Build a lightweight, fast (hence "Kwik"), multi-tenant e-commerce backend platform that allows multiple businesses to manage their product catalogs, customers, and sales through a shared infrastructure while maintaining data isolation.

## Primary Goals

1. **Multi-Tenancy**: Support multiple independent businesses (tenants) on a single platform
2. **Product Management**: Enable tenants to create, update, hide, archive, and manage products with photos
3. **Authentication & Authorization**: Secure JWT-based authentication system for different user roles
4. **API-First Design**: RESTful API with OpenAPI documentation for easy integration
5. **Performance**: Leverage Quarkus for fast startup and low memory footprint
6. **Code Quality**: Maintain clean architecture with automated testing and code formatting

## Scope

### In Scope

- Multi-tenant product catalog management (CRUD operations)
- Product states: SHOWN, HIDDEN, ARCHIVED
- Product photos/images support
- JWT authentication and authorization
- Tenant management
- User management with password-based authentication
- REST API endpoints for management and customer-facing operations
- Database migrations with Liquibase
- BDD acceptance testing

### Out of Scope (Current Phase)

- Order processing and shopping cart
- Payment gateway integrations
- Shipping and fulfillment
- Customer user accounts and profiles
- Search and filtering capabilities
- Inventory management
- Email notifications
- Admin dashboard UI

## Key Requirements

### Functional

- Tenants can manage their own product catalogs independently
- Products have name, description, price (BRL), photos, and state
- Managers can create, update, hide, archive products
- Customers can view only products in SHOWN state
- JWT-based authentication for admin users
- Data isolation between tenants

### Non-Functional

- Fast startup time (Quarkus benefit)
- Clean architecture with clear layer boundaries
- High test coverage (unit, integration, BDD)
- Google Java Style compliance
- Automated code formatting
- Database schema versioning

## Architecture Principles

1. **Modular Design**: Separate modules for auth, product, tenant, and common concerns
2. **Ports & Adapters**: Clear separation between domain logic and infrastructure
3. **Layer Boundaries**: Domain → Application → Infrastructure (driven/driving)
4. **Domain-Driven Design**: Rich domain models with business logic encapsulation
5. **Dependency Inversion**: Domain depends on nothing, infrastructure depends on domain

## Success Criteria

- Working REST API for product management
- Multi-tenant data isolation verified
- JWT authentication functional
- All tests passing
- Code formatted to Google standards
- API documented with OpenAPI/Swagger

