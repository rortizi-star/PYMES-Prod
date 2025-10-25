# Documentación API de Reportes Financieros
## Sistema ERP - Universidad de San Carlos de Guatemala

### 1. Descripción General

El API de Reportes Financieros es un componente del sistema ERP que permite generar cinco tipos principales de reportes contables:

- **Balance General**: Estado financiero que muestra activos, pasivos y patrimonio
- **Estado de Resultados**: Reporte de ingresos y gastos en un período
- **Libro Mayor**: Detalle de movimientos por cuenta contable
- **Libro Diario**: Registro cronológico de asientos contables
- **Auxiliares**: Reportes auxiliares de cuentas específicas

### 2. Estructura de Clases y Arquitectura

#### 2.1 Diagrama de Componentes

```
📦 gob.gt.com.lab.demo.financialreport
├── 🎮 FinancialReportController.java     (REST Controller)
└── 🔧 FinancialReportService.java        (Lógica de negocio)

📦 gob.gt.com.lab.demo.entity
├── 📊 JournalEntry.java                   (Asientos contables)
├── 📋 JournalEntryLine.java               (Líneas de asientos)
└── 🏦 Account.java                        (Plan de cuentas)

📦 gob.gt.com.lab.demo.repository
├── 📚 JournalEntryRepository.java         (Repositorio JPA)
├── 📝 JournalEntryLineRepository.java     (Repositorio JPA)
└── 🗂️ AccountRepository.java              (Repositorio JPA)

📦 erp-frontend/src/app/financial-reports
├── 🖥️ FinancialReportsComponent.ts        (Componente Angular)
├── 🔌 FinancialReportsApiService.ts       (Servicio HTTP)
└── 📄 financial-reports.component.html    (Vista)
```

#### 2.2 Descripción de Clases Principales

##### FinancialReportController
```java
@RestController
@RequestMapping("/api/financial-reports")
public class FinancialReportController {
    @Autowired
    private FinancialReportService service;
    
    // Endpoints para cada tipo de reporte
}
```
**Responsabilidades:**
- Exposición de endpoints REST
- Validación de parámetros de entrada
- Manejo de respuestas HTTP

##### FinancialReportService
```java
@Service
public class FinancialReportService {
    // Lógica de cálculo de reportes financieros
    public Map<String, Object> getBalanceGeneral(int year, int month) {...}
    public Map<String, Object> getEstadoResultados(int year, int month) {...}
    public Map<String, Object> getLibroMayor(int year, int month) {...}
    public Map<String, Object> getLibroDiario(int year, int month) {...}
    public Map<String, Object> getAuxiliares(int year, int month) {...}
}
```
**Responsabilidades:**
- Procesamiento de datos contables
- Cálculos financieros específicos
- Formateo de resultados

##### Entidades del Modelo de Datos

**JournalEntry (Asiento Contable)**
```java
@Entity
@Table(name = "journal_entries")
public class JournalEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String entryNumber;
    
    @Column(nullable = false)
    private Date date;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String status; // Draft, Posted, Reversed, Cancelled
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @OneToMany(mappedBy = "journalEntry", cascade = CascadeType.ALL)
    private List<JournalEntryLine> lines;
}
```

**JournalEntryLine (Línea de Asiento)**
```java
@Entity
@Table(name = "journal_entry_lines")
public class JournalEntryLine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "journal_entry_id")
    private JournalEntry journalEntry;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    private String description;
    
    @Column(nullable = false)
    private BigDecimal debit = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private BigDecimal credit = BigDecimal.ZERO;
    
    private Integer lineNumber;
}
```

**Account (Plan de Cuentas)**
```java
@Entity
@Table(name = "accounts")
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Account parent;
    
    private Integer level;
    private String type;        // ASSET, LIABILITY, EQUITY, INCOME, EXPENSE
    private Boolean isDetail;   // true para cuentas de detalle
    private Boolean isActive;
    private String nature;      // DEBIT, CREDIT
}
```

### 3. Endpoints del API

#### 3.1 Base URL
```
http://localhost:8082/api/financial-reports
```

#### 3.2 Endpoints Disponibles

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| GET | `/balance-general` | Balance General | year, month |
| GET | `/estado-resultados` | Estado de Resultados | year, month |
| GET | `/libro-mayor` | Libro Mayor | year, month |
| GET | `/libro-diario` | Libro Diario | year, month |
| GET | `/auxiliares` | Reportes Auxiliares | year, month |

#### 3.3 Parámetros de Consulta

- **year** (int): Año del reporte (ej: 2024)
- **month** (int): Mes del reporte (1-12)

#### 3.4 Formato de Respuesta

Todos los endpoints devuelven un objeto JSON con la estructura:
```json
{
  "message": "Descripción del reporte generado",
  "data": [...], // Array con datos del reporte (cuando aplique)
  "metadata": {
    "year": 2024,
    "month": 12,
    "generatedAt": "timestamp"
  }
}
```

### 4. Ejemplos de Uso del API

#### 4.1 Consultar Balance General

**Request (cURL):**
```bash
curl -X GET "http://localhost:8082/api/financial-reports/balance-general?year=2024&month=12" \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "Balance general generado (mock)"
}
```

#### 4.2 Consultar Estado de Resultados

**Request (cURL):**
```bash
curl -X GET "http://localhost:8082/api/financial-reports/estado-resultados?year=2024&month=12" \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "Estado de resultados generado (mock)"
}
```

#### 4.3 Consultar Libro Mayor

**Request (cURL):**
```bash
curl -X GET "http://localhost:8082/api/financial-reports/libro-mayor?year=2024&month=12" \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "Libro mayor generado (mock)"
}
```

#### 4.4 Consultar Libro Diario

**Request (cURL):**
```bash
curl -X GET "http://localhost:8082/api/financial-reports/libro-diario?year=2024&month=12" \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "Libro diario generado (mock)"
}
```

#### 4.5 Consultar Auxiliares

**Request (cURL):**
```bash
curl -X GET "http://localhost:8082/api/financial-reports/auxiliares?year=2024&month=12" \
  -H "Content-Type: application/json"
```

**Response:**
```json
{
  "message": "Auxiliares generados (mock)"
}
```

#### 4.6 Ejemplo con Postman

**Configuración de Request en Postman:**

1. **Método**: GET
2. **URL**: `http://localhost:8082/api/financial-reports/balance-general`
3. **Params**:
   - Key: `year`, Value: `2024`
   - Key: `month`, Value: `12`
4. **Headers**:
   - `Content-Type: application/json`

#### 4.7 Códigos de Respuesta HTTP

| Código | Descripción | Ejemplo de Uso |
|--------|-------------|----------------|
| 200 | OK | Reporte generado exitosamente |
| 400 | Bad Request | Parámetros inválidos (year/month fuera de rango) |
| 500 | Internal Server Error | Error en el servidor o base de datos |

#### 4.8 Manejo de Errores

**Ejemplo de Error 400 (Parámetros Inválidos):**
```bash
curl -X GET "http://localhost:8082/api/financial-reports/balance-general?year=abc&month=13"
```

**Response de Error:**
```json
{
  "error": "Bad Request",
  "message": "Invalid parameters: year must be numeric and month must be between 1-12",
  "timestamp": "2024-12-12T10:30:00Z"
}
```

### 5. Frontend Angular

#### 5.1 FinancialReportsApiService
```typescript
@Injectable({ providedIn: 'root' })
export class FinancialReportsApiService {
  private baseUrl = 'http://localhost:8082/api/financial-reports';

  constructor(private http: HttpClient) {}

  getBalanceGeneral(year: number, month: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/balance-general`, { 
      params: { year, month } 
    });
  }

  getEstadoResultados(year: number, month: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/estado-resultados`, { 
      params: { year, month } 
    });
  }

  // ... otros métodos similares
}
```

#### 5.2 Funcionalidades del Componente
- Selección de año y mes
- Generación de reportes dinámicos
- Exportación a Excel (.xlsx)
- Exportación a PDF
- Visualización con gráficos (Chart.js)
- Tabla de datos con Material Design

### 6. Validaciones y Reglas de Negocio

#### 6.1 Validación de Asientos Contables
El sistema valida la regla fundamental de contabilidad de doble partida:
```java
// En JournalEntryService.saveJournalEntry()
if (totalDebit.compareTo(totalCredit) != 0) {
    throw new IllegalArgumentException(
        "Debe = Haber. Suma de débitos y créditos no cuadra."
    );
}
```

#### 6.2 Control de Períodos Contables
- Solo permite registrar asientos en períodos ABIERTOS
- Valida que las fechas estén dentro del período activo

### 7. Tecnologías Utilizadas

**Backend:**
- Spring Boot 3.5.5
- Java 21
- JPA/Hibernate
- Base de datos H2 (en memoria)
- Maven como gestor de dependencias

**Frontend:**
- Angular 17
- Material Design
- Chart.js para gráficos
- XLSX para exportación Excel
- jsPDF para exportación PDF

### 8. Configuración de Base de Datos

#### 8.1 Archivo application.properties

El sistema está configurado para usar base de datos H2 en memoria para desarrollo:

```properties
# H2 Database (En memoria - temporal)
spring.datasource.url=jdbc:h2:mem:pymes
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.application.name=demo

# H2 Console (para debugging - accede a http://localhost:8082/h2-console)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Puerto del servidor
server.port=8082
```

#### 8.2 Características de la Configuración

**Base de Datos H2:**
- **Tipo**: En memoria (temporal)
- **URL**: `jdbc:h2:mem:pymes`
- **Usuario**: `sa` (sin contraseña)
- **Ventajas**: Rápida, ideal para desarrollo y pruebas
- **Limitaciones**: Los datos se pierden al reiniciar

**Configuración JPA:**
- **DDL Auto**: `create-drop` - Crea y elimina esquema automáticamente
- **Show SQL**: `true` - Muestra consultas SQL en consola
- **Format SQL**: `true` - Formatea SQL para mejor legibilidad

**Puerto del Servidor:**
- **Puerto**: `8082` - Evita conflictos con otras aplicaciones
- **URL Base**: `http://localhost:8082`

**Consola H2:**
- **Habilitada**: `true` - Para debugging y consultas directas
- **Ruta**: `http://localhost:8082/h2-console`
- **Utilidad**: Permite inspeccionar datos y ejecutar SQL manualmente

#### 8.3 Acceso a la Consola H2

Para acceder a la base de datos directamente:
1. Navegar a: `http://localhost:8082/h2-console`
2. Configurar conexión:
   - **JDBC URL**: `jdbc:h2:mem:pymes`
   - **User Name**: `sa`
   - **Password**: (dejar vacío)
3. Hacer clic en "Connect"

### 9. Patrones de Diseño Implementados

- **Repository Pattern**: Acceso a datos a través de repositorios JPA
- **Service Layer**: Lógica de negocio separada del controlador
- **DTO Pattern**: Transferencia de datos entre capas
- **Observer Pattern**: Uso de Observables en Angular
- **Dependency Injection**: Inyección de dependencias con @Autowired

---

*Documento generado para examen parcial - Universidad de San Carlos de Guatemala*
*Sistema ERP - Módulo de Reportes Financieros*