package com.interface21.web.servlet;

import junit.framework.TestCase;

import com.interface21.web.mock.MockHttpServletRequest;
import com.interface21.web.mock.MockHttpServletResponse;
import com.interface21.web.mock.MockServletContext;
import com.interface21.web.servlet.theme.AbstractThemeResolver;
import com.interface21.web.servlet.theme.CookieThemeResolver;
import com.interface21.web.servlet.theme.FixedThemeResolver;
import com.interface21.web.servlet.theme.SessionThemeResolver;

/**
 * @author Jean-Pierre Pawlak
 * @author Juergen Hoeller
 * @since 19.06.2003
 */
public class ThemeResolverTestSuite extends TestCase {

	private static final String TEST_THEME_NAME = "test.theme";

	public ThemeResolverTestSuite(String name) {
		super(name);
	}

	private void internalTest(ThemeResolver themeResolver, boolean shouldSet) {
		// create mocks
		MockServletContext context = new MockServletContext();
		MockHttpServletRequest request = new MockHttpServletRequest(context, "GET", "/test");
		MockHttpServletResponse response = new MockHttpServletResponse();
		// check original theme
		String themeName = themeResolver.resolveThemeName(request);
		assertEquals(themeName, AbstractThemeResolver.DEFAULT_THEME);
		// set new theme name
		try {
			themeResolver.setThemeName(request, response, TEST_THEME_NAME);
			if (!shouldSet)
				fail("should not be able to set Theme name");
			// check new theme namelocale
			themeName = themeResolver.resolveThemeName(request);
			assertEquals(themeName, TEST_THEME_NAME);
		} catch (IllegalArgumentException ex) {
			if (shouldSet)
				fail("should be able to set Theme name");
		}
	}

	public void testFixedThemeResolver() {
		internalTest(new FixedThemeResolver(), false);
	}

	public void testCookieThemeResolver() {
		internalTest(new CookieThemeResolver(), true);
	}

	public void testSessionThemeResolver() {
		internalTest(new SessionThemeResolver(), true);
	}
}