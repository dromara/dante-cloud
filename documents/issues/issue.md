## **已知问题的研究**

##### **1、其它服务调用SecurityContextHolder.getContext().getAuthentication().getPrincipal()只能获取到用户名的问题**

通过对代码的跟踪发现，外部服务在访问/oauth/check_token的过程中，使用的是RemoteTokenServices。该类中使用DefaultUserAuthenticationConverter，对Token进行转换。该类中有一段核心代码：

具体代码如下：

    if (map.containsKey("user_name")) {
        Object principal = map.get("user_name");
        Collection<? extends GrantedAuthority> authorities = this.getAuthorities(map);
        if (this.userDetailsService != null) {
            UserDetails user = this.userDetailsService.loadUserByUsername((String)map.get("user_name"));
            authorities = user.getAuthorities();
            principal = user;
        }
        return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
    } else {
        return null;
    }
   
通过该段代码可以明显的看到，想要getPrincipal返回完成的Oauth2User对象，就需要的注入userDetailsService。这就说明，单纯的外部服务是无法拿到完整的用户信息对象，除非可以连接到Oauth数据库，或者对应的TokenStore。这样的方式无可厚非。但是外部与Oauth无法解耦。

现在想到的该问题的几种解决办法：

    （1）ResourceServer可以获取到UserDetailsService，就是可以直连到Oauth2的数据库。
    （2）利用RestTemplate的方式，远程访问/current/user。（这里还没有弄明白，yml中配置的user-info-uri什么时候起作用或者如何调用。）
    