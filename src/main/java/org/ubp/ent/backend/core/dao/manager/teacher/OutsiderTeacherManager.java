package org.ubp.ent.backend.core.dao.manager.teacher;

import org.springframework.stereotype.Service;
import org.ubp.ent.backend.core.dao.manager.teacher.contact.address.AddressManager;
import org.ubp.ent.backend.core.dao.manager.teacher.contact.email.EmailManager;
import org.ubp.ent.backend.core.dao.manager.teacher.contact.phone.PhoneManager;
import org.ubp.ent.backend.core.dao.repository.teacher.OutsiderTeacherRepository;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.address.AddressTypeRepository;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.email.EmailTypeRepository;
import org.ubp.ent.backend.core.dao.repository.teacher.contact.phone.PhoneTypeRepository;
import org.ubp.ent.backend.core.domains.teacher.OutsiderTeacherDomain;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.AddressTypeResourceNotFoundException;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.EmailTypeResourceNotFoundException;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.PhoneTypeResourceNotFoundException;
import org.ubp.ent.backend.core.exceptions.database.notfound.impl.TeacherResourceNotFoundException;
import org.ubp.ent.backend.core.model.teacher.OutsiderTeacher;
import org.ubp.ent.backend.core.model.teacher.contact.address.Address;
import org.ubp.ent.backend.core.model.teacher.contact.address.AddressType;
import org.ubp.ent.backend.core.model.teacher.contact.email.Email;
import org.ubp.ent.backend.core.model.teacher.contact.email.EmailType;
import org.ubp.ent.backend.core.model.teacher.contact.phone.Phone;
import org.ubp.ent.backend.core.model.teacher.contact.phone.PhoneType;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anthony on 16/01/2016.
 */
@Service
public class OutsiderTeacherManager {

    @Inject
    private OutsiderTeacherRepository outsiderTeacherRepository;

    @Inject
    private AddressManager addressManager;
    @Inject
    private AddressTypeRepository addressTypeRepository;

    @Inject
    private EmailManager emailManager;
    @Inject
    private EmailTypeRepository emailTypeRepository;

    @Inject
    private PhoneManager phoneManager;
    @Inject
    private PhoneTypeRepository phoneTypeRepository;

    public OutsiderTeacher create(OutsiderTeacher model) {
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + OutsiderTeacher.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + OutsiderTeacher.class.getName() + " which already has an ID.");
        }
        OutsiderTeacherDomain domain = new OutsiderTeacherDomain(model);
        domain = outsiderTeacherRepository.saveAndFlush(domain);
        model.setId(domain.getId());

        return domain.toModel();
    }

    public OutsiderTeacher findOneById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot find a " + OutsiderTeacher.class.getName() + " with a null id.");
        }
        OutsiderTeacherDomain domain = outsiderTeacherRepository.findOne(id);

        if (domain == null) {
            throw new TeacherResourceNotFoundException("No " + OutsiderTeacher.class.getName() + " found for id :" + id);
        }

        return domain.toModel();
    }

    public List<OutsiderTeacher> findAll() {
        return outsiderTeacherRepository.findAll().parallelStream()
                .map(OutsiderTeacherDomain::toModel)
                .collect(Collectors.toList());
    }

    public Address addAddress(Long teacherId, Address model) {
        if (teacherId == null) {
            throw new IllegalArgumentException("Cannot find a " + OutsiderTeacher.class.getName() + " with a null id.");
        }
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Address.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Address.class.getName() + " which already has an ID.");
        }

        if (model.getType().getId() == null || !addressTypeRepository.exists(model.getType().getId())) {
            throw new AddressTypeResourceNotFoundException("No " + AddressType.class.getName() + " found for id :" + model.getType().getId());
        }

        model = addressManager.create(model);

        OutsiderTeacher fetched = findOneById(teacherId);
        fetched.getContact().addAddress(model);
        outsiderTeacherRepository.saveAndFlush(new OutsiderTeacherDomain(fetched));
        return model;
    }

    public void removeAddress(Long addressId) {
        addressManager.delete(addressId);
    }

    public Email addEmail(Long teacherId, Email model) {
        if (teacherId == null) {
            throw new IllegalArgumentException("Cannot find a " + OutsiderTeacher.class.getName() + " with a null id.");
        }
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Email.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Email.class.getName() + " which already has an ID.");
        }

        if (model.getType().getId() == null || !emailTypeRepository.exists(model.getType().getId())) {
            throw new EmailTypeResourceNotFoundException("No " + EmailType.class.getName() + " found for id :" + model.getType().getId());
        }

        model = emailManager.create(model);

        OutsiderTeacher fetched = findOneById(teacherId);
        fetched.getContact().addEmail(model);
        outsiderTeacherRepository.saveAndFlush(new OutsiderTeacherDomain(fetched));
        return model;
    }

    public void removeEmail(Long emailId) {
        emailManager.delete(emailId);
    }

    public Phone addPhone(Long teacherId, Phone model) {
        if (teacherId == null) {
            throw new IllegalArgumentException("Cannot find a " + OutsiderTeacher.class.getName() + " with a null id.");
        }
        if (model == null) {
            throw new IllegalArgumentException("Cannot persist a null " + Phone.class.getName());
        }
        if (model.getId() != null) {
            throw new AlreadyDefinedInOnNonPersistedEntity("Cannot persist a " + Phone.class.getName() + " which already has an ID.");
        }

        if (model.getType().getId() == null || !phoneTypeRepository.exists(model.getType().getId())) {
            throw new PhoneTypeResourceNotFoundException("No " + PhoneType.class.getName() + " found for id :" + model.getType().getId());
        }

        model = phoneManager.create(model);

        OutsiderTeacher fetched = findOneById(teacherId);
        fetched.getContact().addPhone(model);
        outsiderTeacherRepository.saveAndFlush(new OutsiderTeacherDomain(fetched));
        return model;
    }

    public void removePhone(Long phoneId) {
        phoneManager.delete(phoneId);
    }

}
