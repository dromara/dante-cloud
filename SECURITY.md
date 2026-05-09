# Security Policy

Thanks for helping keep dante-cloud and the wider [dromara](https://github.com/dromara) ecosystem secure.

## Reporting a vulnerability

**Please do not open a public issue, discussion, or pull request that describes a suspected vulnerability** — that gives attackers a head-start before a fix lands.

Use one of these private channels instead:

1. **GitHub's Private Vulnerability Reporting (preferred).**
   Open a private security advisory at <https://github.com/dromara/dante-cloud/security/advisories/new>.
   GitHub automatically routes it to the maintainers, keeps the discussion private until you and the maintainers agree to publish, and (optionally) assigns a CVE on publish.

2. **Email.**
   Email reporting is not available yet; please use GitHub PVR.

## What to include

A good report contains:

- A clear description of the vulnerability and its impact.
- Steps to reproduce against a specific commit SHA or release tag.
- Affected code locations (`file:line`) where useful.
- A suggested fix or mitigation if you have one.
- Whether you'd like credit in the published advisory and, if so, under what name.

## Scope and supported versions

dante-cloud is a Spring Cloud / Spring Authorization Server-based microservice and OAuth2 framework supporting password, client_credentials, authorization_code, social login flows, as well as opaque token handling and built-in XSS defences. The maintainers support security fixes on the latest 4.x minor release line. Older releases will not generally receive backports.

In-scope vulnerability classes include:

- Authentication / authorization bypass
- OAuth2 / OIDC protocol implementation flaws (PKCE, redirect-URI validation, scope escalation, state/nonce handling)
- JWT signature / claims validation flaws
- Opaque token handling flaws – token introspection SSRF, missing or insufficient validation of introspection responses, token replay attacks, scope/audience mismatch after introspection
- Session fixation, social-login account linking flaws
- Cross-site scripting (XSS) in authorization endpoints, consent pages, error templates, or any administrative UI – both reflected and stored, including bypasses of the framework's built-in XSS protections
- SSRF in IdP / userinfo endpoint fetching
- SQL injection in dynamic / criteria-builder paths
- Insecure deserialization in token codecs
- Cryptographic misuse in stored secrets / signing keys
- CSRF on sensitive OAuth2 endpoints (authorize, logout, etc.)

Out-of-scope:

- Findings against intentionally trusted-admin features (config-driven behaviour that the framework explicitly delegates to the integrator).
- Issues that require an attacker to already have full database / server access.
- Best-practice complaints without a concrete impact (e.g. "this header should be set", "TLS version should be raised").

## Process

After you submit:

1. A maintainer will acknowledge receipt within roughly 1 week.
2. We'll triage the report: confirm severity, scope, and reproducibility.
3. We'll work with you on a fix and a coordinated disclosure timeline (typically up to 90 days, longer if the fix is structural).
4. On publication, we credit you in the advisory unless you ask not to be credited.

## Hall of fame

Reporters who have helped harden dante-cloud via responsible disclosure will be listed here once the corresponding advisory is published.

---

This policy is suggested via [GitHub's "Suggest a security policy" workflow](https://docs.github.com/en/code-security/getting-started/adding-a-security-policy-to-your-repository). Maintainers can edit any section freely; the most important thing is that **a private reporting channel exists** so researchers can submit findings responsibly.