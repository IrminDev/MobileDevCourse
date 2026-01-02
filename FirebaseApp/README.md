# FirebaseApp - Aplicación de Notificaciones Push

## Descripción
Aplicación móvil Android con autenticación de usuarios (normal y administrador), notificaciones push y gestión de usuarios utilizando Firebase.

## Estructura del Proyecto

```
app/src/main/java/com/github/irmin/firebaseapp/
├── MainActivity.kt                    # Actividad principal
├── data/
│   ├── model/
│   │   ├── User.kt                   # Modelo de usuario
│   │   └── Notification.kt           # Modelo de notificación
│   └── repository/
│       ├── AuthRepository.kt         # Repositorio de autenticación
│       └── NotificationRepository.kt # Repositorio de notificaciones
├── service/
│   └── MyFirebaseMessagingService.kt # Servicio FCM
└── ui/
    ├── navigation/
    │   ├── Screen.kt                 # Definición de rutas
    │   └── AppNavigation.kt          # Navegación principal
    ├── screens/
    │   ├── LoginScreen.kt            # Pantalla de login
    │   ├── RegisterScreen.kt         # Pantalla de registro
    │   ├── HomeScreen.kt             # Pantalla principal
    │   ├── ProfileScreen.kt          # Pantalla de perfil
    │   ├── UsersListScreen.kt        # Lista de usuarios (admin)
    │   └── SendNotificationScreen.kt # Enviar notificaciones (admin)
    ├── viewmodel/
    │   ├── AuthViewModel.kt          # ViewModel de autenticación
    │   └── NotificationViewModel.kt  # ViewModel de notificaciones
    └── theme/                        # Tema de la aplicación
```

## Configuración de Firebase Studio

### 1. Habilitar Firebase Authentication

1. Ve a la [Consola de Firebase](https://console.firebase.google.com/)
2. Selecciona tu proyecto `appandroid-1f6bb`
3. En el menú lateral, ve a **Build > Authentication**
4. Haz clic en **Get started**
5. En la pestaña **Sign-in method**, habilita **Email/Password**:
   - Haz clic en "Email/Password"
   - Activa "Enable"
   - Guarda los cambios

#### Solucionar error "CONFIGURATION_NOT_FOUND"

Si ves el error `CONFIGURATION_NOT_FOUND` al crear usuarios, sigue estos pasos adicionales:

**Opción 1: Configurar dominios autorizados**
1. En **Authentication > Settings**
2. Ve a la pestaña **Authorized domains**
3. Asegúrate de que `localhost` y tu dominio estén en la lista
4. Si usas un emulador, agrega `10.0.2.2` (IP del host desde el emulador)

**Opción 2: Descargar nuevamente google-services.json**
1. Ve a **Project Settings** (ícono de engranaje)
2. En la sección **Your apps**, selecciona tu app Android
3. Descarga el archivo `google-services.json` actualizado
4. Reemplaza el archivo en `app/google-services.json`
5. Sincroniza Gradle y reconstruye el proyecto

**Opción 3: Verificar SHA-1 y SHA-256 (para dispositivos físicos)**
1. Genera tus huellas digitales SHA:
   ```bash
   cd android
   gradlew signingReport
   ```
   O desde la raíz del proyecto:
   ```bash
   ./gradlew signingReport
   ```
2. Copia las huellas SHA-1 y SHA-256 de `debug`
3. En Firebase Console, ve a **Project Settings**
4. En tu app Android, haz clic en **Add fingerprint**
5. Pega cada huella (SHA-1 y SHA-256)
6. Descarga nuevamente `google-services.json` y reemplázalo

**Opción 4: Reiniciar la App ID de Firebase**
1. En Firebase Console, ve a **Project Settings**
2. En la sección **Your apps**, elimina la app Android existente
3. Haz clic en **Add app** y selecciona Android
4. Ingresa el package name: `com.github.irmin.firebaseapp`
5. Descarga el nuevo `google-services.json`
6. Reemplázalo en tu proyecto
7. Sincroniza Gradle

### 2. Configurar Cloud Firestore

1. En el menú lateral, ve a **Build > Firestore Database**
2. Haz clic en **Create database**
3. Selecciona **Start in test mode** (para desarrollo)
4. Elige la ubicación más cercana a tus usuarios
5. Haz clic en **Enable**

#### Reglas de Seguridad para Firestore

Ve a **Firestore Database > Rules** y configura las siguientes reglas:

```javascript
rules_version = '2';

service cloud.firestore {
  match /databases/{database}/documents {
    
    // Función auxiliar para verificar si el usuario es admin
    function isAdmin() {
      return request.auth != null && 
             get(/databases/$(database)/documents/users/$(request.auth.uid)).data.admin == true;
    }
    
    // Colección de usuarios
    match /users/{userId} {
      // Cualquier usuario autenticado puede leer su propio documento
      allow read: if request.auth != null && request.auth.uid == userId;
      // Solo el propio usuario puede escribir su documento
      allow write: if request.auth != null && request.auth.uid == userId;
      // Los administradores pueden leer todos los usuarios
      allow read: if isAdmin();
    }
    
    // Colección de notificaciones
    match /notifications/{notificationId} {
      // Leer: Solo el destinatario puede leer sus notificaciones
      allow read: if request.auth != null && 
                    resource.data.recipientId == request.auth.uid;
      // Crear: Usuarios autenticados pueden crear notificaciones
      allow create: if request.auth != null;
      // Actualizar: Solo el destinatario puede actualizar (ej: marcar como leída)
      allow update: if request.auth != null && 
                      resource.data.recipientId == request.auth.uid;
    }
  }
}
```

**IMPORTANTE:** La función `isAdmin()` debe usar `.data.admin` (no `.data.isAdmin`) porque ese es el nombre del campo en Firestore.

### 3. Configurar Firebase Cloud Messaging (FCM)

1. En el menú lateral, ve a **Build > Cloud Messaging**
2. Si es la primera vez, haz clic en **Get started**
3. FCM se configurará automáticamente

**Nota:** Las notificaciones se guardan en Firestore y se visualizan dentro de la app. Para enviar notificaciones push reales que aparezcan en la bandeja del sistema Android, necesitarías implementar Cloud Functions (requiere plan Blaze de pago) o usar Firebase Console manualmente.

#### Crear índices en Firestore

Para que las consultas funcionen correctamente, necesitas crear un índice:

1. Ve a **Firestore Database > Indexes**
2. Haz clic en **Create index**
3. Configura:
   - **Collection ID:** `notifications`
   - **Fields to index:**
     - `recipientId` - Ascending
     - `timestamp` - Descending
   - **Query scope:** Collection
4. Haz clic en **Create**

### 4. (Opcional) Configurar Cloud Functions para notificaciones Push reales

Para que las notificaciones push funcionen automáticamente cuando se crea una notificación en Firestore, necesitas configurar Cloud Functions:

1. Instala Firebase CLI:
   ```bash
   npm install -g firebase-tools
   ```

2. Inicia sesión:
   ```bash
   firebase login
   ```

3. Inicializa Functions:
   ```bash
   cd FirebaseApp
   firebase init functions
   ```

4. Crea la función en `functions/index.js`:

```javascript
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendPushNotification = functions.firestore
  .document('notifications/{notificationId}')
  .onCreate(async (snap, context) => {
    const notification = snap.data();
    
    // Obtener el token FCM del destinatario
    const userDoc = await admin.firestore()
      .collection('users')
      .doc(notification.recipientId)
      .get();
    
    if (!userDoc.exists) {
      console.log('Usuario no encontrado');
      return null;
    }
    
    const userData = userDoc.data();
    const fcmToken = userData.fcmToken;
    
    if (!fcmToken) {
      console.log('Usuario sin token FCM');
      return null;
    }
    
    // Enviar notificación push
    const message = {
      notification: {
        title: notification.title,
        body: notification.body
      },
      data: {
        notificationId: context.params.notificationId,
        senderId: notification.senderId
      },
      token: fcmToken
    };
    
    try {
      await admin.messaging().send(message);
      console.log('Notificación enviada exitosamente');
    } catch (error) {
      console.error('Error enviando notificación:', error);
    }
    
    return null;
  });
```

5. Despliega la función:
   ```bash
   firebase deploy --only functions
   ```

## Uso de la Aplicación

### Roles de Usuario

#### Usuario Normal
- Registro e inicio de sesión con email/contraseña
- Ver y editar perfil personal
- Recibir notificaciones push
- Ver historial de notificaciones

#### Administrador
- Todas las funciones del usuario normal
- Ver lista de usuarios registrados
- Enviar notificaciones a:
  - Un usuario específico
  - Varios usuarios seleccionados
  - Todos los usuarios

### Contraseña Maestra para Administradores

Para registrarse como administrador, se requiere la contraseña maestra:

```
Admin@2024#SecretKey
```

**⚠️ IMPORTANTE:** En producción, cambia esta contraseña en el archivo:
`app/src/main/java/com/github/irmin/firebaseapp/data/repository/AuthRepository.kt`

```kotlin
companion object {
    const val ADMIN_MASTER_PASSWORD = "TU_NUEVA_CONTRASEÑA_SEGURA"
}
```

## Dependencias

El proyecto utiliza las siguientes dependencias principales:

- **Firebase BOM 34.5.0**
  - Firebase Authentication
  - Firebase Firestore
  - Firebase Cloud Messaging
  - Firebase Analytics

- **Jetpack Compose**
  - Material 3
  - Navigation Compose
  - Material Icons Extended

- **AndroidX**
  - Lifecycle ViewModel Compose
  - Activity Compose

## Compilación y Ejecución

1. Abre el proyecto en Android Studio
2. Sincroniza Gradle (File > Sync Project with Gradle Files)
3. Conecta un dispositivo Android o inicia un emulador
4. Ejecuta la aplicación (Run > Run 'app')

## Notas Adicionales

- Las notificaciones push requieren un dispositivo físico o un emulador con Google Play Services
- Para Android 13+, la aplicación solicita permiso de notificaciones automáticamente
- Los tokens FCM se actualizan automáticamente cuando el usuario inicia sesión
- Las notificaciones se almacenan en Firestore para mantener un historial

## Solución de Problemas Comunes

### Error: "CONFIGURATION_NOT_FOUND" al crear usuarios

**Causa:** Firebase Authentication no está completamente configurado o hay problemas con el archivo `google-services.json`.

**Solución:**
1. Descarga nuevamente el archivo `google-services.json` desde Firebase Console
2. Reemplaza el archivo en `app/google-services.json`
3. Agrega las huellas digitales SHA-1 y SHA-256 en Firebase Console (ver instrucciones arriba)
4. Sincroniza Gradle: `./gradlew --refresh-dependencies`
5. Limpia y reconstruye: `Build > Clean Project` y luego `Build > Rebuild Project`

### Error: Las notificaciones no llegan

**Causa:** El token FCM no se está generando o el servicio FCM no está configurado correctamente.

**Solución:**
1. Verifica que el `AndroidManifest.xml` tenga el servicio `MyFirebaseMessagingService`
2. Asegúrate de que el dispositivo/emulador tenga Google Play Services instalado
3. Verifica los permisos de notificación en la configuración del dispositivo
4. Revisa los logs para ver si el token FCM se está generando correctamente

### Error: "Could not resolve firebase dependencies"

**Causa:** Problemas con las dependencias de Firebase en Gradle.

**Solución:**
1. Verifica que tienes conexión a internet
2. Sincroniza Gradle: `File > Sync Project with Gradle Files`
3. Si el problema persiste, ejecuta: `./gradlew --refresh-dependencies`
4. Verifica que la versión de `com.google.gms.google-services` sea compatible (actualmente `4.4.4`)

### No puedo crear cuenta de administrador

**Causa:** La contraseña maestra es incorrecta.

**Solución:**
1. Verifica que estés usando la contraseña correcta: `Admin@2024#SecretKey`
2. Si quieres cambiarla, edita `AuthRepository.kt`:
   ```kotlin
   companion object {
       const val ADMIN_MASTER_PASSWORD = "TuNuevaContraseña"
   }
   ```

### El rol de administrador no funciona correctamente

**Causa:** El campo `isAdmin` en Firestore no está configurado correctamente o los datos del usuario no se están recargando.

**Soluciones:**

1. **Verificar en Firebase Console:**
   - Ve a **Firestore Database**
   - Busca la colección `users`
   - Encuentra tu documento de usuario (con tu UID)
   - Verifica que el campo `isAdmin` sea `true` (booleano, no string)

2. **Corregir manualmente en Firestore:**
   - En Firebase Console, edita el documento del usuario
   - Asegúrate de que `isAdmin` sea tipo `boolean` con valor `true`
   - Guarda los cambios

3. **Recargar datos en la app:**
   - En la pantalla principal, presiona el botón de recarga (ícono de refresh) en el top bar
   - Esto recargará los datos del usuario desde Firestore
   - Verifica que ahora aparezca como "Administrador" y tengas acceso a las opciones de admin

4. **Cerrar sesión y volver a iniciar:**
   - Cierra sesión en la app
   - Vuelve a iniciar sesión
   - Los datos se cargarán frescos desde Firestore

5. **Crear una nueva cuenta de admin:**
   - Si el problema persiste, registra una nueva cuenta
   - Marca el checkbox "Registrarme como Administrador"
   - Ingresa la contraseña maestra: `Admin@2024#SecretKey`
   - Completa el registro

### La aplicación crashea al cerrar sesión con error "PERMISSION_DENIED"

**Causa:** Los listeners de Firestore siguen activos después de cerrar sesión, intentando acceder a datos sin autenticación.

**Solución:**
El código ya incluye la solución con `notificationViewModel.stopListening()`. Si aún ocurre:

1. **Limpia y reconstruye el proyecto:**
   ```bash
   ./gradlew clean build
   ```

2. **Invalida caché de Android Studio:**
   - `File > Invalidate Caches... > Invalidate and Restart`

3. **Desinstala la app del dispositivo/emulador:**
   - Desinstala completamente la app
   - Vuelve a instalarla desde Android Studio

4. **Verifica las reglas de Firestore:**
   - Las reglas deben estar configuradas correctamente como se muestra arriba
   - Las reglas no deben permitir lectura sin autenticación

### La aplicación no compila

**Soluciones:**
1. Limpia el proyecto: `Build > Clean Project`
2. Invalida caché: `File > Invalidate Caches... > Invalidate and Restart`
3. Verifica que tu JDK sea versión 11 o superior
4. Asegúrate de tener la última versión de Android Studio
5. Ejecuta: `./gradlew clean build`

## Licencia

Este proyecto es para fines educativos del curso de Desarrollo Móvil.

