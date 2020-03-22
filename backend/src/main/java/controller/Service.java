package controller;

import model.Arbiter;
import model.Nota;
import model.Participant;
import model.Proba;
import repositories.ArbiterRepository;
import repositories.NotaRepository;
import repositories.ParticipantRepository;
import repositories.ProbaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service {

    private ParticipantRepository participantRepository;
    private ArbiterRepository arbiterRepository;
    private ProbaRepository probaRepository;
    private NotaRepository notaRepository;

    public Service(Properties props) {
        participantRepository = new ParticipantRepository(props);
        arbiterRepository = new ArbiterRepository(props);
        probaRepository = new ProbaRepository(props);
        notaRepository = new NotaRepository(props);
    }

    public boolean login(String username, String password) {
        if (arbiterRepository.findByUsername(username).getPassword().equals(password)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    public Arbiter saveArbiter(Arbiter arbiter) {
        try {
            return arbiterRepository.save(arbiter);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public void deleteArbiter(Integer id) {
        arbiterRepository.deleteById(id);
    }

    public Arbiter getArbiter(Integer id) {
        return this.arbiterRepository.find(id);
    }

    public Iterable<Arbiter> allArbiter() {
        return this.arbiterRepository.findAll();
    }

    public Proba saveProba(Proba proba) {
        try {
            return probaRepository.save(proba);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void deleteProba(Integer id) {
        this.probaRepository.deleteById(id);
    }

    public Proba getProba(Integer id) {
        return probaRepository.find(id);
    }

    public Iterable<Proba> allProba() {
        return probaRepository.findAll();
    }

    public Participant saveParticipant(Participant participant) {
        try {
            return participantRepository.save(participant);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void deleteParticipant(Integer id) {
        participantRepository.deleteById(id);
    }

    public Participant getParticipant(Integer id) {
        return participantRepository.find(id);
    }

    public Iterable<Participant> allParticipant() {
        return participantRepository.findAll();
    }

    public Nota saveNota(Nota nota) {
        try {
            return this.notaRepository.save(nota);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void deleteNota(Integer id) {
        notaRepository.deleteById(id);
    }

    public Nota getNota(Integer id) {
        return notaRepository.find(id);
    }

    public Iterable<Nota> allNota() {
        return notaRepository.findAll();
    }

    public List<Nota> filterNotaProba(Integer probaId) {
        List<Nota> result = new ArrayList<>();

        List<Nota> test = StreamSupport
                .stream(notaRepository.findAll().spliterator(), false)
                .filter(element -> element.getProbaId().equals(probaId))
                .collect(Collectors.toList());


        this.notaRepository.findAll().forEach(element -> {
            if (element.getProbaId().equals(probaId)) {
                result.add(element);
            }
        });

        return test;
    }
}