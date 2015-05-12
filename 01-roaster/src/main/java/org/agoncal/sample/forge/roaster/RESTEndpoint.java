package org.agoncal.sample.forge.roaster;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
public class RESTEndpoint {

	public static void main(String[] args) {

		String named = "MyEndpoint";
		String path = "toto";
		RESTMethod[] methods = {RESTMethod.GET, RESTMethod.POST, RESTMethod.PUT, RESTMethod.DELETE};

		final JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);
		javaClassSource.setPackage("org.agoncal.myproj").setName(named).addAnnotation(Path.class).setStringValue("/" + path);

		for (RESTMethod method : methods) {
			MethodSource<?> doGet = javaClassSource.addMethod().setPublic().setName(method.getMethodName())
					.setReturnType("javax.ws.rs.core.Response");
			doGet.addAnnotation(method.getMethodAnnotation());

			switch (method) {
				case GET:
					doGet.addAnnotation(javax.ws.rs.Produces.class).setStringArrayValue(new String[] {MediaType.TEXT_PLAIN});
					doGet.setBody("return Response.ok(\"method " + method.getMethodName() + " invoked\").build();");
					break;
				case POST:
					javaClassSource.addImport(UriBuilder.class);
					doGet.addAnnotation(javax.ws.rs.Consumes.class).setStringArrayValue(
							new String[] {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON});
					doGet.addParameter(String.class, "entity");
					doGet.setBody("return Response.created(UriBuilder.fromResource(" + named + ".class).build()).build();");
					break;
				case PUT:
					javaClassSource.addImport(UriBuilder.class);
					doGet.addAnnotation(javax.ws.rs.Consumes.class).setStringArrayValue(
							new String[] {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON});
					doGet.addParameter(String.class, "entity");
					doGet.setBody("return Response.created(UriBuilder.fromResource(" + named + ".class).build()).build();");
					break;
				case DELETE:
					doGet.addAnnotation(javax.ws.rs.Path.class).setStringValue("/{id}");
					doGet.addParameter(Long.class, "id").addAnnotation(PathParam.class).setStringValue("id");
					doGet.setBody("return Response.noContent().build();");
					break;
			}
		}

		System.out.println(javaClassSource);
	}

	enum RESTMethod {
		GET("doGet", javax.ws.rs.GET.class), POST("doPost", javax.ws.rs.POST.class), PUT("doPut", javax.ws.rs.PUT.class), DELETE("doDelete",
				javax.ws.rs.DELETE.class);

		private String methodName;

		private Class methodAnnotation;

		private RESTMethod(String methodName, Class methodAnnotation) {
			this.methodName = methodName;
			this.methodAnnotation = methodAnnotation;
		}

		public Class getMethodAnnotation() {
			return methodAnnotation;
		}

		public String getMethodName() {
			return methodName;
		}
	}
}
