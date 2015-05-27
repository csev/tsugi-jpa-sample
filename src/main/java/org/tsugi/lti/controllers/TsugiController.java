/**
 * Copyright 2014 Unicon (R)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tsugi.lti.controllers;

import java.util.Enumeration;

// import org.tsugi.lti.LTIRequest;
import org.tsugi.zippy.TsugiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * This Tsugi controller should be protected by OAuth 1.0a (on the /oauth path)
 * Key "key" and secret "secret"
 */
@Controller
@RequestMapping("/tsugi")
public class TsugiController extends BaseController {

    @Autowired
    TsugiService tsugiService;

    @RequestMapping({"", "/"})
    public String home(HttpServletRequest req, Principal principal, Model model) {
System.out.println("YO");
        commonModelPopulate(req, principal, model);

        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.print(paramName);
            System.out.print("=");
            String[] paramValues = req.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                if ( i > 0 ) System.out.print(", ");
                System.out.print(paramValue);
            }
            System.out.println();
        }

        model.addAttribute("name", "tsugi");
        System.out.println("check="+tsugiService.check());
        return "tsugi"; // name of the template
    }

}
