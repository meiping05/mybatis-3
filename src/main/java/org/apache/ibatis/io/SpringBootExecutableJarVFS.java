/**
 *    Copyright 2009-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.io;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author Lucky
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SpringBootExecutableJarVFS extends VFS {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public List<String> list(final URL url, final String path) throws IOException {
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);

//        Resource[] resources = resolver.getResources(path + "/**/*.class");
        Resource[] resources = resolver.getResources(url.toString()+"/**/*.class");


        final List<String> resourcePaths = new ArrayList<>();
        for (Resource resource : resources) {
            resourcePaths.add(preserveSubpackageName(resource.getURI(), path));
        }


//        final List<String> resourcePaths = Arrays.asList(resources).stream()
//                .map(resource -> {
//                    try {
//                        return preserveSubpackageName(resource.getURI(), path);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .collect(toList());
        return resourcePaths;
    }


    protected static String preserveSubpackageName(final URI uri, final String rootPath) {
        final String uriStr = uri.toString();
        // we must return the uri with everything before the rootpath stripped off
        final int start = uriStr.indexOf(rootPath);
        return uriStr.substring(start, uriStr.length());
    }

}
