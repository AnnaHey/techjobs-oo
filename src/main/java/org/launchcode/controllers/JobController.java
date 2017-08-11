package org.launchcode.controllers;

        import org.launchcode.models.*;
        import org.launchcode.models.forms.JobForm;
        import org.launchcode.models.data.JobData;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.validation.Errors;
        import org.springframework.web.bind.annotation.*;

        import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();


@RequestMapping(value = "", method = RequestMethod.GET)
public String index(Model model, int id) {

    Job jobs = jobData.findById(id);
    model.addAttribute("jobs", jobs);
    return "job-detail";
    }

@RequestMapping(value = "add", method = RequestMethod.GET)
public String add(Model model) {
    model.addAttribute(new JobForm());
    return "new-job";
    }

@RequestMapping(value = "add", method = RequestMethod.POST)
public String add(@ModelAttribute @Valid JobForm jobForm, Errors errors, Model model) {

    if (errors.hasErrors())  {
        model.addAttribute(new JobForm());
        model.addAttribute("title", "Has Errors");
        return "new-job";
        }

    Job newJob = new Job(jobForm.getName(), jobData.getEmployers().findById(jobForm.getEmployerId()), jobData.getLocations().findById(jobForm.getLocationId()), jobData.getPositionTypes().findById(jobForm.getPositionTypeId()), jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));
    jobData.add(newJob);

    return "redirect:/job?id=" + newJob.getId();

    }
}
