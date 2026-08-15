package hr.tis.academy.controller;

import hr.tis.academy.common.dto.StoreForm;
import hr.tis.academy.mappers.StoreFormMapper;
import hr.tis.academy.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stores-view")
public class StoreViewController {
    private final StoreService storeService;
    private final StoreFormMapper storeFormMapper;

    public StoreViewController(StoreService storeService, StoreFormMapper storeFormMapper) {
        this.storeService = storeService;
        this.storeFormMapper = storeFormMapper;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("stores", storeService.findAll());
        return "stores/list";
    }

    @GetMapping("/{storeId}")
    public String detail(@PathVariable("storeId") Long id, Model model) {
        model.addAttribute("store", storeService.findById(id));
        return "stores/detail";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("form", new StoreForm());
        return "stores/form";
    }

    @PostMapping
    public String saveStore(@Valid @ModelAttribute StoreForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "stores/form";
        }
        storeService.save(storeFormMapper.toDto(form));
        return "redirect:/stores-view";
    }

    @GetMapping("/{storeId}/edit")
    public String edit(@PathVariable("storeId") Long id, Model model) {
        StoreForm sf = storeFormMapper.toEntity(storeService.findById(id));
        model.addAttribute("storeForm", sf);
        model.addAttribute("action",   id + "/edit");
        return "stores/form";
    }

    @PostMapping("/{storeId}/edit")
    public String editStore(@PathVariable("storeId") Long storeId, @Valid @ModelAttribute("storeForm") StoreForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "stores/form";
        }

        storeService.update(storeId, storeFormMapper.toDto(form));
        return "redirect:/stores-view";
    }

    @PostMapping("/{storeId}/delete")
    public String delete(@PathVariable("storeId") Long storeId) {
        storeService.deleteById(storeId);
        return "redirect:/stores-view";
    }
}
