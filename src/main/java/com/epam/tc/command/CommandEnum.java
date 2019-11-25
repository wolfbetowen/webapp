package com.epam.tc.command;

import com.epam.tc.command.impl.*;

/**
 * @author alex raby
 * @version 1.0
 * returns, depending on the name, the class of the implementing Command
 */
public enum CommandEnum {

  ADD_TRAINING_TO_STUDENT {
    {
      this.command = new AddTrainingToStudentCommand();
    }
  },
  LOG_IN {
    {
      this.command = new AuthorizationCommand();
    }
  },
  LOG_OUT {
    {
      this.command = new LogOutCommand();
    }
  },
  REGISTRATION {
    {
      this.command = new RegistrationUserCommand();
    }
  },
  REGISTRATION_PAGE {
    {
      this.command = new RegistrationPageCommand();
    }
  },
  TRAININGS_PAGE {
    {
      this.command = new TrainingsPageCommand();
    }
  },
  MAIN_PAGE {
    {
      this.command = new MainPageCommand();
    }
  },
  TRAININGS_INFORMATION_PAGE {
    {
      this.command = new TrainingsInformationPageCommand();
    }
  },
  LOG_IN_PAGE {
    {
      this.command = new LogInPageCommand();
    }
  },
  CABINET {
    {
      this.command = new CabinetPageCommand();
    }
  },
  TOPIC_PAGE {
    {
      this.command = new TopicPageCommand();
    }
  },
  CREATE_TEXT {
    {
      this.command = new CreateTextCommand();
    }
  },
  UPDATE_INFORMATION_ABOUT_TRAINING {
    {
      this.command = new UpdateInformationAboutTrainingCommand();
    }
  },
  ADD_TOPIC_FOR_TRAINING {
    {
      this.command = new AddTopicForTrainingCommand();
    }
  },
  ADD_TASK_FOR_TRAINING {
    {
      this.command = new AddTaskForTrainingCommand();
    }
  },
  MANAGEMENT_PAGE {
    {
      this.command = new ManagementPageCommand();
    }
  },
  CREATE_TRAINING {
    {
      this.command = new CreateTrainingCommand();
    }
  },
  UPDATE_TRAININGS_TOPIC {
    {
      this.command = new UpdateTrainingsTopicCommand();
    }
  },
  UPDATE_USER_TYPE {
    {
      this.command = new UpdateUserTypeCommand();
    }
  },
  MARK_TOPIC {
    {
      this.command = new MarkTopicCommand();
    }
  },
  TASK_PAGE {
    {
      this.command = new TaskPageCommand();
    }
  },
  UPDATE_TRAININGS_TASK {
    {
      this.command = new UpdateTrainingsTaskCommand();
    }
  },
  SEND_SOLUTION {
    {
      this.command = new SendSolutionCommand();
    }
  },
  STUDENT_MANAGEMENT {
    {
      this.command = new StudentManagementCommand();
    }
  },
  MENTORING {
    {
      this.command = new MentoringCommand();
    }
  },
  SET_MARK_FOR_TASK {
    {
      this.command = new SetMarkCommand();
    }
  },
  OFFER_DATE {
    {
      this.command = new OfferDateCommand();
    }
  },
  CONSULTATION_FOR_MENTOR {
    {
      this.command = new ConsultationForMentorCommand();
    }
  },
  SEND_AGREEMENT {
    {
      this.command = new SendAgreementCommand();
    }
  },
  ORDER_CONSULTATION {
    {
      this.command = new OrderConsultationCommand();
    }
  },
  SEND_ORDER_CONSULTATION {
    {
      this.command = new SendOrderConsultationCommand();
    }
  },
  SET_LOCAL {
    {
      this.command = new SetLocalCommand();
    }
  },
  CARD_MANAGEMENT {
    {
      this.command = new CardManagementCommand();
    }
  },
  REPLENISH_CARD {
    {
      this.command = new ReplenishCardCommand();
    }
  },
  TRANSFER_MONEY {
    {
      this.command = new TransferMoneyCommand();
    }
  },
  PAYMENT_CONSULTATION {
    {
      this.command = new PaymentConsultationCommand();
    }
  },
  SET_FINAL_GRADE {
    {
      this.command = new SetFinalGradeCommand();
    }
  },
  CLOSE_RECEPTION {
    {
      this.command = new CloseReceptionCommand();
    }
  },
  DELETE_USER {
    {
      this.command = new DeleteUserCommand();
    }
  },
  DELETE_TRAINING {
    {
      this.command = new DeleteTrainingCommand();
    }
  },
  DELETE_TOPIC {
    {
      this.command = new DeleteTopicCommand();
    }
  },
  DELETE_TASK {
    {
      this.command = new DeleteTaskCommand();
    }
  },
  GIVE_FEEDBACK {
    {
      this.command = new GiveFeedBackCommand();
    }
  },
  SET_LOCAL_CABINET {
    {
      this.command = new SetLocalCabinetCommand();
    }
  },
  ADD_PAYMENT_CARD {
    {
      this.command = new AddPaymentCardCommand();
    }
  },
  REVIEWS {
    {
      this.command = new ReviewsCommand();
    }
  };

  /**
   * enum field Command {@link Command}
   */
  Command command;

  /**
   * returns Command the appropriate  name
   * @return Command {@link Command}
   */
  public Command getCurrentCommand() {
    return command;
  }
}