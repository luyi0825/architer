package io.github.test;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

public class URLReWriteRequest implements HttpServletRequest {

    private final HttpServletRequest httpServletRequest;

    private final String uri;

    public URLReWriteRequest(HttpServletRequest httpServletRequest, String uri) {
        this.httpServletRequest = httpServletRequest;
        this.uri = uri;
    }


    @Override
    public String getAuthType() {
        return httpServletRequest.getAuthType();
    }

    @Override
    public Cookie[] getCookies() {
        return httpServletRequest.getCookies();
    }

    @Override
    public long getDateHeader(String s) {
        return httpServletRequest.getDateHeader(s);
    }

    @Override
    public String getHeader(String s) {
        return httpServletRequest.getHeader(s);
    }

    @Override
    public Enumeration<String> getHeaders(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return httpServletRequest.getHeaderNames();
    }

    @Override
    public int getIntHeader(String s) {
        return httpServletRequest.getIntHeader(s);
    }

    @Override
    public String getMethod() {
        return httpServletRequest.getMethod();
    }

    @Override
    public String getPathInfo() {
        return httpServletRequest.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        return httpServletRequest.getPathTranslated();
    }

    @Override
    public String getContextPath() {
        return httpServletRequest.getContextPath();
    }

    @Override
    public String getQueryString() {
        return httpServletRequest.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return httpServletRequest.getQueryString();
    }

    @Override
    public boolean isUserInRole(String s) {
        return httpServletRequest.isUserInRole(s);
    }

    @Override
    public Principal getUserPrincipal() {
        return httpServletRequest.getUserPrincipal();
    }

    @Override
    public String getRequestedSessionId() {
        return httpServletRequest.getRequestedSessionId();
    }

    @Override
    public String getRequestURI() {
        return uri;
    }

    @Override
    public StringBuffer getRequestURL() {
        return new StringBuffer(httpServletRequest.getRemoteAddr()).append(":").append(httpServletRequest.getRemotePort()).append(getRequestURI());
    }

    @Override
    public String getServletPath() {
        return httpServletRequest.getServletPath();
    }

    @Override
    public HttpSession getSession(boolean b) {
        return httpServletRequest.getSession(b);
    }

    @Override
    public HttpSession getSession() {
        return httpServletRequest.getSession();
    }

    @Override
    public String changeSessionId() {
        return httpServletRequest.changeSessionId();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return httpServletRequest.isRequestedSessionIdValid();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return httpServletRequest.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return httpServletRequest.isRequestedSessionIdFromURL();
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
        return httpServletRequest.authenticate(httpServletResponse);
    }

    @Override
    public void login(String s, String s1) throws ServletException {
        httpServletRequest.login(s, s1);
    }

    @Override
    public void logout() throws ServletException {
        httpServletRequest.logout();
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return httpServletRequest.getParts();
    }

    @Override
    public Part getPart(String s) throws IOException, ServletException {
        return httpServletRequest.getPart(s);
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
        return httpServletRequest.upgrade(aClass);
    }

    @Override
    public Object getAttribute(String s) {
        return httpServletRequest.getAttribute(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return httpServletRequest.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return httpServletRequest.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        httpServletRequest.setCharacterEncoding(s);
    }

    @Override
    public int getContentLength() {
        return httpServletRequest.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        return httpServletRequest.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        return httpServletRequest.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return httpServletRequest.getInputStream();
    }

    @Override
    public String getParameter(String s) {
        return httpServletRequest.getParameter(s);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return httpServletRequest.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String s) {
        return httpServletRequest.getParameterValues(s);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return httpServletRequest.getParameterMap();
    }

    @Override
    public String getProtocol() {
        return httpServletRequest.getProtocol();
    }

    @Override
    public String getScheme() {
        return httpServletRequest.getScheme();
    }

    @Override
    public String getServerName() {
        return httpServletRequest.getServerName();
    }

    @Override
    public int getServerPort() {
        return httpServletRequest.getServerPort();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return httpServletRequest.getReader();
    }

    @Override
    public String getRemoteAddr() {
        return httpServletRequest.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return httpServletRequest.getRemoteHost();
    }

    @Override
    public void setAttribute(String s, Object o) {
        httpServletRequest.setAttribute(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        httpServletRequest.removeAttribute(s);
    }

    @Override
    public Locale getLocale() {
        return httpServletRequest.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return httpServletRequest.getLocales();
    }

    @Override
    public boolean isSecure() {
        return httpServletRequest.isSecure();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return httpServletRequest.getRealPath(s);
    }

    @Override
    public int getRemotePort() {
        return httpServletRequest.getRemotePort();
    }

    @Override
    public String getLocalName() {
        return httpServletRequest.getLocalName();
    }

    @Override
    public String getLocalAddr() {
        return httpServletRequest.getLocalAddr();
    }

    @Override
    public int getLocalPort() {
        return httpServletRequest.getLocalPort();
    }

    @Override
    public ServletContext getServletContext() {
        return httpServletRequest.getServletContext();
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return httpServletRequest.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return httpServletRequest.startAsync(servletRequest, servletResponse);
    }

    @Override
    public boolean isAsyncStarted() {
        return httpServletRequest.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        return httpServletRequest.isAsyncSupported();
    }

    @Override
    public AsyncContext getAsyncContext() {
        return httpServletRequest.getAsyncContext();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return httpServletRequest.getDispatcherType();
    }


}
