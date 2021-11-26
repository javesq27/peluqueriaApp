package ub.tp.reservaPeluquerias.Model;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class WebController implements WebMvcConfigurer
{
    @Autowired
    UserRepository userDatabase;
	@Autowired
    AppointmentRepository appointmentDatabase;
	@Autowired
    BranchRepository branchDatabase;
	@Autowired
    ServiceRepository serviceDatabase;
    @Autowired
    CustomerRepository customerDatabase;
    @Autowired
    SendMailService sendMailService;

    Scheduler schedule = new Scheduler();
    
    @GetMapping("/")
    public String mainPageRedirect()
    {
        return "redirect:/guest";
    }

    @GetMapping("/guest")
    public String mainPage(Model model)
    {
        model.addAttribute("List", serviceDatabase.findAll());
        return "main";
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) String error)
    {
        model.addAttribute("error", Boolean.valueOf(error));
        return "login";
    }

    @GetMapping("/user")
    public String userMainPage()
    {
        return "userMain";
    }


    @GetMapping("/guest/AppointmentOrder")
    public String appointmentOrderAppointmentInput(Model model, Appointment appointment, @RequestParam(value = "error", required = false) String error)
    {
        model.addAttribute("error", Boolean.valueOf(error));
        model.addAttribute("serviceList", serviceDatabase.findAll());
        model.addAttribute("branchList", branchDatabase.findAll());
        return "inputAppointment";
    }    
    
    @PostMapping("/guest/AppointmentPending")
    public String appointmentPending(Appointment appointment, @RequestParam String name, @RequestParam String email)
    {   
        if (!appointment.getDate().isAfter(LocalDate.now()))
        {
            return "redirect:/guest/AppointmentOrder?error=true";
        }
        if (!customerDatabase.existsByEmail(email))
        {
            customerDatabase.save(new Customer().emailSetting(email).nameSetting(name));
        }
        Integer id = customerDatabase.findByEmail(email).getId();
        appointment.setCustomerId(id);
        appointment.setDuration(serviceDatabase.findById(appointment.getServiceId()).get().getServiceTime());
        appointmentDatabase.save(appointment);

        String receiver = email.trim();
        String body = "Su turno está en espera, puede consultar su estado en nuesta Web con el siguiente código de seguimiento: "
                        + appointmentDatabase.findTop1ByCustomerIdOrderByIdDesc(id).getId() + " .";
        sendMailService.sendEmail(receiver, body, "Turno en espera.");

        return "main";
    }

    @GetMapping("/user/List/Appointments")
    public String appointmentList(Model model, @RequestParam(value = "order", required = false) String order, @RequestParam(value = "error", required = false) String error)
    {
        if (order == null)
        {
            order = "false";
        }
        model.addAttribute("error", Boolean.valueOf(error));
        model.addAttribute("branches", branchDatabase);
        model.addAttribute("customers", customerDatabase);
        model.addAttribute("order", Boolean.parseBoolean(order));
        model.addAttribute("List", appointmentDatabase.findByConfirmed(Boolean.parseBoolean(order)));
        return "listAppointment";
    }

    @PostMapping("/user/ConfirmServices")
    public String serviceConfirmed(Service service)
    {
        serviceDatabase.save(service);
        return "redirect:/user/List/Services";
    }

    @GetMapping("/user/List/Services")
    public String serviceList(Model model, Service service)
    {
        model.addAttribute("List", serviceDatabase.findAll());
        return "listService";
    }

    @GetMapping("/user/deleteService/{id}")
    public String deleteService(@PathVariable(name = "id") String id, HttpServletRequest request)
    {
        Integer idVal = Integer.valueOf(id);
        if (!appointmentDatabase.existsByServiceId(idVal) && request.isUserInRole("ADMIN"))
        {
            serviceDatabase.deleteById(idVal);
        }
        return "redirect:/user/List/Services";
    }

    @PostMapping("/user/ConfirmBranches")
    public String branchConfirmed(Branch branch)
    {
        branchDatabase.save(branch);
        return "redirect:/user/List/Branches";
    }

    @GetMapping("/user/List/Branches")
    public String branchList(Model model, Branch branch)
    {
        model.addAttribute("branchList", branchDatabase.findAll());
        return "listBranch";
    }

    @GetMapping("/user/deleteBranch/{id}")
    public String deleteBranch(@PathVariable(name = "id") String id, HttpServletRequest request)
    {
        Integer idVal = Integer.valueOf(id);
        if (!appointmentDatabase.existsByBranchId(idVal) && request.isUserInRole("ADMIN"))
        {
            branchDatabase.deleteById(idVal);
        }
        return "redirect:/user/List/Branches";
    }

    @PostMapping("/user/ConfirmUsers")
    public String userConfirmed(User user)
    {   
        userDatabase.save(user);
        return "redirect:/user/List/Users";
    }

    @GetMapping("/user/List/Users")
    public String userList(Model model, User user)
    {
        model.addAttribute("List", userDatabase.findAll());
        
        return "listUser";
    }

    @GetMapping("/user/deleteUser/{id}")
    public String deleteUser(@PathVariable(name = "id") String id, HttpServletRequest request)
    {   
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String dbUsername = userDatabase.getOne(Integer.valueOf(id)).getUsername();
        if (request.isUserInRole("ADMIN") && username != dbUsername)
        {
            userDatabase.deleteById(Integer.valueOf(id));
        }
        return "redirect:/user/List/Users";
    }

    @GetMapping("/user/List/Customer")
    public String customerList(Model model)
    {
        model.addAttribute("list", customerDatabase.findAll());
        return "listCustomer";
    }

    @GetMapping("/user/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable(name = "id") String id)
    {
        if (!appointmentDatabase.existsByCustomerId(Integer.valueOf(id)))
        {
            customerDatabase.deleteById(Integer.valueOf(id));
        }
        return "redirect:/user/List/Customer";
    }

    @PostMapping("/guest/checkAppointment")
    public String checkAppointmentResult(Model model, String search)
    {   
        Integer s = Integer.valueOf(search);
        model.addAttribute("exists", appointmentDatabase.existsById(s));
        if (appointmentDatabase.existsById(s))
        {
            model.addAttribute("confirmed", appointmentDatabase.findById(s).get().isConfirmed());
            model.addAttribute("time", appointmentDatabase.findById(s).get().getTime());
            model.addAttribute("date", appointmentDatabase.findById(s).get().getDate());
        }
        return "main";
    }

    @GetMapping("/user/AppointmentDeletion/{id}")
    @Transactional
    public String appointmentDeletion(@PathVariable(name = "id") String id)
    {
        Appointment temp = appointmentDatabase.findById(Integer.valueOf(id)).get();
        String receiver = customerDatabase.findById(temp.getCustomerId()).get().getEmail();
            String body = "Su turno para la fecha " + temp.getDate() + " a las "+ temp.getTime() + 
                            " fue rechazado.\nIntente elegir un turno en una fecha, hora o sucursal distinta.";
            sendMailService.sendEmail(receiver, body, "Turno Rechazado");

        appointmentDatabase.deleteById(Integer.valueOf(id));
        return "redirect:/user/List/Appointments";
    }

    @GetMapping("/user/AppointmentConfirmation/{id}")
    @Transactional
    public String appointmentConfirmation(@PathVariable(name = "id") String id)
    {
        Appointment temp = appointmentDatabase.findById(Integer.valueOf(id)).get();
        Branch branchTemp = branchDatabase.findById(temp.getBranchId().intValue());
        schedule.setOpeningTime(branchTemp.getOpen());
        schedule.setClosingTime(branchTemp.getClosed());
        if (schedule.isAppointmentAvailable(appointmentDatabase.findByBranchIdAndConfirmedEquals(temp.getBranchId(), true), temp))
        {
            appointmentDatabase.save(temp.confirmedAppointment(true));

            String receiver = customerDatabase.findById(temp.getCustomerId()).get().getEmail();
            String body = "Su turno fue confirmado para la fecha " + temp.getDate() + " a las "+ temp.getTime() + 
                            " en la sucursal de " + branchTemp.getAddress()+".\n" + "Su código de seguimiento es: "
                            + temp.getId() + "."; 
            sendMailService.sendEmail(receiver, body, "Turno Confirmado");

            return "redirect:/user/List/Appointments";
        }
        return "redirect:/user/List/Appointments?error=true";
    }
    
    @Transactional
    @GetMapping("/user/deleteCompleted")
    public String deleteCompleted()
    {
        appointmentDatabase.deleteByDateBefore(LocalDate.now());
        return "redirect:/user/List/Appointments";
    }
}