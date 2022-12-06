var Project2 = (function () {
    var displaySectionTable = function (data) {
        var sections = data.sections;
        var output = document.getElementById("output");
        var text;
        var html = " ";

        for (var i = 0; i < sections.length; ++i) {
            var section = sections[i];

            html += ("<table>");
            html += ("<tbody>");

            //create header

            html += ("<h3>" + section.description + " - " + section.crn + " - " +
                section.subjectid + " " + section.num + " - " + section.section)
            html += ("</h3>");

            //create daterow

            html += ("<tr>");            
            html += ("<td>");

            html += ("<span> Associated Term :" + section.termname + "</span><br>");
            html += ("<span>Level :" + section.level + "</span><br>");
            html += ("<span>" + section.credits + " Credits</span><br>");


            html += ("<tr>");
            html += ("<th scope = \"col\">Time</th>");
            html += ("<th scope = \"col\">Days</th>");
            html += ("<th scope = \"col\">Where</th>");
            html += ("<th scope = \"col\">Date Ranger</th>");
            html += ("<th scope = \"col\">Schedule Type</th>");
            html += ("<th scope = \"col\">Instructor(s)</th>");
            html += ("</tr>");

            html += ("<tr>");
            html += ("<td>" + section.start + " - " + section.end + "</td>");
            html += ("<td>" + section.days + "</td>");
            html += ("<td>" + section.where + "</td>");
            html += ("<td>" + section.termstart + " - " + section.termend + "</td>");
            html += ("<td>" + section.scheduletype + "</td>");
            html += ("<td>" + section.instructor + "</td>");
            html += ("</tr>");

            html += ("</td>");
            html += ("</tr>");

            html += ("</tbody>");
            html += ("</table>");
        }
        output.innerHTML = html;
    };
    return {
        getSchedule: function () {
            $.ajax({
                url: "http://localhost:8180/CS425_Project2/main/RegistrationServlet",
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    if (data.sections.length === 0) {
                        $("#output").html("No classes have been registered! Register for classes here:"
                            + "<br><br><a href=\"term_1.jsp\">"
                            + "Register for Classes</a>");
                    }
                    else {
                        displaySectionTable(data);
                    }
                }
            });
        },
        search: function () {
            $.ajax({
                url: "http://localhost:8180/CS425_Project2/main/SearchServlet",
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    if (data.sections.length === 0) {
                        $("#output").html("No classes were found that meet the search criteria ");
                    }
                    else {
                        displaySectionTable(data);
                    }
                }
            });
        },
        register: function () {
            $.ajax({
                url: "http://localhost:8180/CS425_Project2/main/RegistrationServlet",
                method: 'POST',
                data: $('#regform').serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.sections.length === 0) {
                        $("#output").html("No classes were found that meet your search criteria ");
                    }
                    else {
                        displaySectionTable(data);
                    }

                }

            });

        },
        unregister: function () {
            var that = this;
            var url = "http://localhost:8180/CS425_Project2/main/RegistrationServlet?";
            url = url + $("#delform").serialize();
            $.ajax({
                url: url,
                method: 'DELETE',
                dataType: 'json',
                success: function (data) {
                    $("#output").prepend("Course dropped successfully!");
                    that.getSchedule();
                }
            });
        }
    };
})();